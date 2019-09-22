package br.com.timtec.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.timtec.model.Hotel;
import br.com.timtec.repositories.HotelRepository;

@RestController
@RequestMapping("/services/hoteis")
public class HoteisService {
	
	
	
	@Autowired
	private HotelRepository hotelRepository;
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Hotel>> findByCidade(String cidade, @RequestHeader(name="X-API-Version", defaultValue="1.0") String versaoApi) {
		
		System.out.println("Versão da API: " + versaoApi);
		
		
		List<Hotel> hoteis = hotelRepository.findByCidade(cidade); 
		
		StringBuilder builder = new StringBuilder();
		for (Hotel hotel : hoteis) {
			
			hotel.add(Link.valueOf("</quartos/1>; title=\"Quarto básico\"; rel=\"quarto\""));
			builder.append(calculateHotelHash(hotel));
		}

		
		return ResponseEntity.ok().eTag(builder.toString()).body(hoteis);
		
	}
	
	
	@RequestMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
		
		
		Hotel hotel = hotelRepository.findOne(id);
		
		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
		
		
		return ResponseEntity.ok().eTag(calculateHotelHash(hotel)).body(hotel);
		
		
	}
	
	
	@RequestMapping(method=RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> updateHotel(Hotel hotel, @PathVariable("id") Integer id, @RequestHeader(name="If-Match") String etag) {
		
		ResponseEntity<?> responseHotel = findById(id);
		if (!responseHotel.getStatusCode().is2xxSuccessful()) {
			return responseHotel;
		}
		
		String calculoETag = responseHotel.getHeaders().getETag();
		if (!calculoETag.equals(etag)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		hotel.setIdentifier(id);
		hotelRepository.save(hotel);
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	private String calculateHotelHash(Hotel hotel) {
		StringBuilder builder = new StringBuilder();
		DigestUtils.appendMd5DigestAsHex(new byte[]{hotel.getIdentifier().byteValue()}, builder);
		DigestUtils.appendMd5DigestAsHex(hotel.getNome().getBytes(), builder);
		DigestUtils.appendMd5DigestAsHex(hotel.getEndereco().getBytes(), builder);
		DigestUtils.appendMd5DigestAsHex(hotel.getCidade().getBytes(), builder);
		
		return builder.toString();
	}

}
