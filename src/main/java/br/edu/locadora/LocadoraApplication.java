package br.edu.locadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//Sem o (exclude = {DataSourceAutoConfiguration.class}) o servidor não abre
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LocadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocadoraApplication.class, args);
	}
	//VÁ INSTALAR O REDIS!
}
