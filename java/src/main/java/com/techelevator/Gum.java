package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Consumable {

	public Gum(String location, String name, BigDecimal price) {
		super(location, name, price, "Gum");
	}

	@Override
	public String message() {
		return "Chew Chew, Yum!";
	}

}
