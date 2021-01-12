package com.techelevator;

import java.math.BigDecimal;

public class Candy extends Consumable {

	public Candy(String location, String name, BigDecimal price) {
		super(location, name, price, "Candy");
	}

	@Override
	public String message() {
		return "Munch Munch, Yum!";
	}

}
