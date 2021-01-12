package com.techelevator;

import java.math.BigDecimal;

public abstract class Consumable {
	
	private String location;
	private String name;
	private BigDecimal price;
	private String type;
	private int quantity = 5;
	
	
	public Consumable(String location, String name, BigDecimal price, String type) {
		this.location = location;
		this.name = name;
		this.price = price;
		this.type = type;
	}


	public String getLocation() {
		return location;
	}


	public String getName() {
		return name;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public String getType() {
		return type;
	}


	public int getQuantity() {
		return quantity;
	}
	
	public void sold() {
		quantity--;
	}
	
	public abstract String message();

}
