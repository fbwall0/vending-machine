package com.techelevator;

import java.math.BigDecimal;

public class Drink extends Consumable {

	public Drink(String location, String name, BigDecimal price) {
		super(location, name, price, "Drink");
	}

	@Override
	public String message() {
		return "Glug Glug, Yum!";
	}

}
