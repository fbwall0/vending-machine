package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class ChipTest {

	@Test
	public void chip_is_at_location_a1() {
		Chip chip = new Chip("A1", "Chip", new BigDecimal("10.00"));
		String expected = "A1";
		String actual = chip.getLocation();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void chip_is_called_chip() {
		Chip chip = new Chip("A1", "Chip", new BigDecimal("10.00"));
		String expected = "Chip";
		String actual = chip.getName();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void chip_costs_10_00() {
		Chip chip = new Chip("A1", "Chip", new BigDecimal("10.00"));
		BigDecimal expected = new BigDecimal("10.00");
		BigDecimal actual = chip.getPrice();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void chip_is_returns_message_munch_munch_yum() {
		Chip chip = new Chip("A1", "Chip", new BigDecimal("10.00"));
		String expected = "Crunch Crunch, Yum!";
		String actual = chip.message();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void chip_is_returns_type_chip() {
		Chip chip = new Chip("A1", "Chip", new BigDecimal("10.00"));
		String expected = "Chip";
		String actual = chip.getType();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void chip_is_returns_quantity_5() {
		Chip chip = new Chip("A1", "Chip", new BigDecimal("10.00"));
		int expected = 5;
		int actual = chip.getQuantity();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void chip_is_returns_quantity_4() {
		Chip chip = new Chip("A1", "Chip", new BigDecimal("10.00"));
		int expected = 4;
		chip.sold();
		int actual = chip.getQuantity();
		Assert.assertEquals(expected, actual);
	}

}
