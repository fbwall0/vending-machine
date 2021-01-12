package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class DrinkTest {

	@Test
	public void drink_is_at_location_a1() {
		Drink drink = new Drink("A1", "Drink", new BigDecimal("10.00"));
		String expected = "A1";
		String actual = drink.getLocation();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void drink_is_called_drink() {
		Drink drink = new Drink("A1", "Drink", new BigDecimal("10.00"));
		String expected = "Drink";
		String actual = drink.getName();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void drink_costs_10_00() {
		Drink drink = new Drink("A1", "Drink", new BigDecimal("10.00"));
		BigDecimal expected = new BigDecimal("10.00");
		BigDecimal actual = drink.getPrice();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void drink_is_returns_message_munch_munch_yum() {
		Drink drink = new Drink("A1", "Drink", new BigDecimal("10.00"));
		String expected = "Glug Glug, Yum!";
		String actual = drink.message();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void drink_is_returns_type_drink() {
		Drink drink = new Drink("A1", "Drink", new BigDecimal("10.00"));
		String expected = "Drink";
		String actual = drink.getType();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void drink_is_returns_quantity_5() {
		Drink drink = new Drink("A1", "Drink", new BigDecimal("10.00"));
		int expected = 5;
		int actual = drink.getQuantity();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void drink_is_returns_quantity_4() {
		Drink drink = new Drink("A1", "Drink", new BigDecimal("10.00"));
		int expected = 4;
		drink.sold();
		int actual = drink.getQuantity();
		Assert.assertEquals(expected, actual);
	}

}
