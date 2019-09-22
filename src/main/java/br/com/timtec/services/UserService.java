package br.com.timtec.services;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {
	
	
	
	
	
	@RequestMapping("/user")
	public Principal getUser(Principal principal) {
		return principal;
	}

}
