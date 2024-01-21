package com.example.reactive;

import java.util.UUID;

public record Greeting(String id, String value) {

	public static Greeting create() {
		String id = UUID.randomUUID().toString().substring(0, 11).toUpperCase();
		String value = "Hello Reactive Spring";
		return new Greeting(id, value);
	}

	public Greeting update(String value) {
		return new Greeting(this.id, this.value.replace("Reactive Spring", value));
	}

}
