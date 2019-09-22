package br.com.timtec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class AppViagensApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppViagensApplication.class, args);
	}
}
