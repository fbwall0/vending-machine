package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class GumTest {

	@Test
	public void gum_is_at_location_a1() {
		Gum gum = new Gum("A1", "Gum", new BigDecimal("10.00"));
		String expected = "A1";
		String actual = gum.getLocation();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void gum_is_called_gum() {
		Gum gum = new Gum("A1", "Gum", new BigDecimal("10.00"));
		String expected = "Gum";
		String actual = gum.getName();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void gum_costs_10_00() {
		Gum gum = new Gum("A1", "Gum", new BigDecimal("10.00"));
		BigDecimal expected = new BigDecimal("10.00");
		BigDecimal actual = gum.getPrice();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void gum_is_returns_message_munch_munch_yum() {
		Gum gum = new Gum("A1", "Gum", new BigDecimal("10.00"));
		String expected = "Chew Chew, Yum!";
		String actual = gum.message();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void gum_is_returns_type_gum() {
		Gum gum = new Gum("A1", "Gum", new BigDecimal("10.00"));
		String expected = "Gum";
		String actual = gum.getType();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void gum_is_returns_quantity_5() {
		Gum gum = new Gum("A1", "Gum", new BigDecimal("10.00"));
		int expected = 5;
		int actual = gum.getQuantity();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void gum_is_returns_quantity_4() {
		Gum gum = new Gum("A1", "Gum", new BigDecimal("10.00"));
		int expected = 4;
		gum.sold();
		int actual = gum.getQuantity();
		Assert.assertEquals(expected, actual);
	}

}
