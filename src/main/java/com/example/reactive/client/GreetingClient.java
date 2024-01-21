package com.example.reactive.client;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.reactive.Greeting;

import lombok.extern.java.Log;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/client")
@Log
public class GreetingClient {

	WebClient client = WebClient.create("http://localhost:8080");
	
	@GetMapping("/{num}")
	public String getAllGreetings(@PathVariable int num) {

		Flux<Greeting> greetingFlux = client.get().uri("/greeting/size/"+num).retrieve().bodyToFlux(Greeting.class);
		
		greetingFlux.subscribe(new Consumer<Greeting>() {
			List<Greeting> greetings = new ArrayList<>();
			@Override
			public void accept(Greeting t) {
				log.info(t.toString());
				greetings.add(t);
				
			}
			
		});
		
		return "Async processing returned, " + Instant.now();
		
	}
	
}
