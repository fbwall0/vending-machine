package com.techelevator;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseMenu;

public class VendingMachineCLITest {
	private ByteArrayOutputStream output;
	private List<Consumable> options = new ArrayList<>();
	private Menu testMenu;
	private PurchaseMenu testPurchaseMenu;
	private VendingMachineCLI cli;

	@Before
	public void setup() throws IOException {
		output = new ByteArrayOutputStream();
		options.add(new Chip("A1", "BagoChips", new BigDecimal("0.95")));
		options.add(new Candy("B2", "Sweets", new BigDecimal("0.95")));
		options.add(new Drink("C3", "Cola", new BigDecimal("0.95")));
		options.add(new Gum("D4", "Chewy", new BigDecimal("0.95")));	
		
		Menu testMenu = getMenuForTesting();
		PurchaseMenu testPurchaseMenu = getPurchaseMenuForTesting();
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
	}
	
	@Test
	public void returns_correct_change() throws IOException {
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
		String actual = cli.getChange(new BigDecimal("5.40"));
		String expected = "21 quarters, 1 dime, 1 nickel";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void returns_correct_change_with_pennies() throws IOException {
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
		String actual = cli.getChange(new BigDecimal("5.01"));
		String expected = "20 quarters, 1 penny";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void is_able_to_purchase_product() throws IOException {
		testPurchaseMenu = getPurchaseMenuForTestingWithUserInput("A1\n");
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
		BigDecimal actual = cli.selectProduct(new BigDecimal("5"), options);
		BigDecimal expected = new BigDecimal("4.05");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void is_unable_to_purchase_product() throws IOException {
		testPurchaseMenu = getPurchaseMenuForTestingWithUserInput("A1\n");
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
		BigDecimal actual = cli.selectProduct(new BigDecimal("0.94"), options);
		BigDecimal expected = new BigDecimal("0.94");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void is_unable_to_purchase_soldout_product() throws IOException {
		testPurchaseMenu = getPurchaseMenuForTestingWithUserInput("A1\nA1\nA1\nA1\nA1\nA1\n");
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
		BigDecimal money = new BigDecimal("10");
		cli.selectProduct(money, options);
		cli.selectProduct(money, options);
		cli.selectProduct(money, options);
		cli.selectProduct(money, options);
		cli.selectProduct(money, options);
		BigDecimal actual = cli.selectProduct(new BigDecimal("1.00"), options);
		BigDecimal expected = new BigDecimal("1.00");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void able_to_feed_money_into_the_vending_machine() throws IOException {
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
		
		BigDecimal actual = cli.feedMoney(new BigDecimal("0.94"), 5);
		BigDecimal expected = new BigDecimal("5.94");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void unable_to_feed_bad_bills_into_the_vending_machine() throws IOException {
		VendingMachineCLI cli = new VendingMachineCLI(testMenu, testPurchaseMenu);
		
		BigDecimal actual = cli.feedMoney(new BigDecimal("0.94"), 7);
		BigDecimal expected = new BigDecimal("0.94");
		
		assertEquals(expected, actual);
	}
	
	
	private Menu getMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new Menu(input, output);
	}

	private Menu getMenuForTesting() {
		return getMenuForTestingWithUserInput("1\n");
	}
	
	private PurchaseMenu getPurchaseMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new PurchaseMenu(input, output);
	}

	private PurchaseMenu getPurchaseMenuForTesting() {
		return getPurchaseMenuForTestingWithUserInput("A1\n");
	}
	

}
