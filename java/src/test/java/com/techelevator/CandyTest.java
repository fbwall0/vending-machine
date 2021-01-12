package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class CandyTest {

	@Test
	public void candy_is_at_location_a1() {
		Candy candy = new Candy("A1", "Candy", new BigDecimal("10.00"));
		String expected = "A1";
		String actual = candy.getLocation();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void candy_is_called_candy() {
		Candy candy = new Candy("A1", "Candy", new BigDecimal("10.00"));
		String expected = "Candy";
		String actual = candy.getName();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void candy_costs_10_00() {
		Candy candy = new Candy("A1", "Candy", new BigDecimal("10.00"));
		BigDecimal expected = new BigDecimal("10.00");
		BigDecimal actual = candy.getPrice();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void candy_is_returns_message_munch_munch_yum() {
		Candy candy = new Candy("A1", "Candy", new BigDecimal("10.00"));
		String expected = "Munch Munch, Yum!";
		String actual = candy.message();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void candy_is_returns_type_candy() {
		Candy candy = new Candy("A1", "Candy", new BigDecimal("10.00"));
		String expected = "Candy";
		String actual = candy.getType();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void candy_is_returns_quantity_5() {
		Candy candy = new Candy("A1", "Candy", new BigDecimal("10.00"));
		int expected = 5;
		int actual = candy.getQuantity();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void candy_is_returns_quantity_4() {
		Candy candy = new Candy("A1", "Candy", new BigDecimal("10.00"));
		int expected = 4;
		candy.sold();
		int actual = candy.getQuantity();
		Assert.assertEquals(expected, actual);
	}

}
