package com.techelevator;

import java.math.BigDecimal;

public class Chip extends Consumable {

	public Chip(String location, String name, BigDecimal price) {
		super(location, name, price, "Chip");
	}

	@Override
	public String message() {
		return "Crunch Crunch, Yum!";
	}

}
