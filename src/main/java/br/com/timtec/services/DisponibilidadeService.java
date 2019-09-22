package br.com.timtec.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.timtec.model.Disponibilidade;
import br.com.timtec.model.Hotel;
import br.com.timtec.model.Quarto;



@RestController
public class DisponibilidadeService {
	
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	@RequestMapping("/disponibilidade/{data}")
	public Disponibilidade recuperarDisponibilidadeParaData(@PathVariable("data") String data) throws ParseException {
		
		
		
		
		Disponibilidade aleatoria = new Disponibilidade();
		Hotel timtec = new Hotel();
		timtec.setNome("TimTec");
		
		Quarto basico = new Quarto();
		basico.setHotel(timtec);
		basico.setTipo("Básico");
		
		aleatoria.setHotel(timtec);
		aleatoria.setQuarto(basico);
		aleatoria.setData(sdf.parse(data));
		
		return aleatoria;
		
		
	}

}
