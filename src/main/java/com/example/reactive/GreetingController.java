package com.example.reactive;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/greeting")
@Log
public class GreetingController {

	@GetMapping("/size/{num}")
	public Flux<Greeting> getMany(@PathVariable int num) {
		
		log.info("** num=" + num);

		return Flux.range(0, num).flatMap(i -> createGreeting());

	}

	private Mono<Greeting> createGreeting() {

		Mono<Greeting> val = Mono.just(Greeting.create()).timeout(Duration.ofSeconds(10L));

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}

		return val;
	}
}
