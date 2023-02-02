package com.bolsadeideas.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner   {
	
	private static final Logger log =LoggerFactory.getLogger(SpringBootReactorApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		Flux <Usuario> nombres = Flux.just("carlo delgado", "allison j salas","martha marallano", "joffre hermosilla", "mila salas","bruce lee","bruce willis")
				.map(nombre ->  new Usuario (nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))//*split separa los nombres y los apellidos
				.filter(usuario ->usuario.getNombre().toLowerCase().equals("bruce"))
				
				
				//*segun el orden de posicion  toUpperCase mayusculas se vera en consola 
		        	  
				
			.doOnNext(usuario -> {
				if(usuario == null) {
				throw new RuntimeException("Nombres no pueden estar vacios");
				}
				System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
				
	})
			.map(usuario -> {
  	  String nombre= usuario.getNombre().toLowerCase();
  	  		usuario.setNombre(nombre);
  	  		return usuario;
  	  });//*segun el orden de posicion  toLowerCase minusculas vera en consola  
		          
				nombres.subscribe(e -> log.info(e.toString()),
						error -> log.error(error.getMessage()),
					new Runnable() {
			
					@Override	
					public void run() {
						log.info("ha finalizado la ejecuciòn");
					
				}

						});
	}


}