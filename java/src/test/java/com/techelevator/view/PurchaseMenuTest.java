package com.techelevator.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.techelevator.Candy;
import com.techelevator.Chip;
import com.techelevator.Consumable;
import com.techelevator.Drink;
import com.techelevator.Gum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PurchaseMenuTest {

	private ByteArrayOutputStream output;
	private List<Consumable> options = new ArrayList<>();

	@Before
	public void setup() {
		output = new ByteArrayOutputStream();
		options.add(new Chip("A1", "BagoChips", new BigDecimal("0.95")));
		options.add(new Candy("B2", "Sweets", new BigDecimal("0.95")));
		options.add(new Drink("C3", "Cola", new BigDecimal("0.95")));
		options.add(new Gum("D4", "Chewy", new BigDecimal("0.95")));		
	}

	@Test
	public void displays_a_list_of_purchaseMenu_options_and_prompts_user_to_make_a_choice() {
		PurchaseMenu purchaseMenu = getPurchaseMenuForTesting();

		purchaseMenu.getChoiceFromOptions(options);

		String expected = "\r\n" + options.get(0).getLocation() + " " + options.get(0).getName() + " $" + options.get(0).getPrice() + "\r\n" + 
				options.get(1).getLocation() + " " + options.get(1).getName() + " $" + options.get(1).getPrice() + "\r\n" + 
				options.get(2).getLocation() + " " + options.get(2).getName() + " $" + options.get(2).getPrice() + "\r\n" + 
				options.get(3).getLocation() + " " + options.get(3).getName() + " $" + options.get(3).getPrice() + "\r\n\n" + "Please choose an option >>> ";


		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void returns_object_corresponding_to_user_choice() {
		PurchaseMenu purchaseMenu = getPurchaseMenuForTestingWithUserInput("A1\n");

		int result = (Integer) purchaseMenu.getChoiceFromOptions(options);
		int expected = 0;

		Assert.assertEquals(expected, result);
	}

	@Test
	public void redisplays_purchaseMenu_if_user_does_not_choose_valid_option() {
		PurchaseMenu purchaseMenu = getPurchaseMenuForTestingWithUserInput("4\nA1\n");

		purchaseMenu.getChoiceFromOptions(options);

		String purchaseMenuDisplay = "\r\n" + options.get(0).getLocation() + " " + options.get(0).getName() + " $" + options.get(0).getPrice() + "\r\n" + 
				options.get(1).getLocation() + " " + options.get(1).getName() + " $" + options.get(1).getPrice() + "\r\n" + 
				options.get(2).getLocation() + " " + options.get(2).getName() + " $" + options.get(2).getPrice() + "\r\n" + 
				options.get(3).getLocation() + " " + options.get(3).getName() + " $" + options.get(3).getPrice() + "\r\n\n" + "Please choose an option >>> ";

		String expected = purchaseMenuDisplay + "\n*** 4 is not a valid option ***\n\r\n" + purchaseMenuDisplay;

		
		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_purchaseMenu_if_user_enters_garbage() {
		PurchaseMenu purchaseMenu = getPurchaseMenuForTestingWithUserInput("Mickey Mouse\nA1\n");

		purchaseMenu.getChoiceFromOptions(options);

		String purchaseMenuDisplay = "\r\n" + options.get(0).getLocation() + " " + options.get(0).getName() + " $" + options.get(0).getPrice() + "\r\n" + 
				options.get(1).getLocation() + " " + options.get(1).getName() + " $" + options.get(1).getPrice() + "\r\n" + 
				options.get(2).getLocation() + " " + options.get(2).getName() + " $" + options.get(2).getPrice() + "\r\n" + 
				options.get(3).getLocation() + " " + options.get(3).getName() + " $" + options.get(3).getPrice() + "\r\n\n" + "Please choose an option >>> ";

		String expected = purchaseMenuDisplay + "\n*** Mickey Mouse is not a valid option ***\n\r\n" + purchaseMenuDisplay;

		
		Assert.assertEquals(expected, output.toString());
	}
	
	@Test
	public void redisplays_purchaseMenu_if_item_is_sold_out() {
		options.get(0).sold();
		options.get(0).sold();
		options.get(0).sold();
		options.get(0).sold();
		options.get(0).sold();
		
		PurchaseMenu purchaseMenu = getPurchaseMenuForTestingWithUserInput("A1\n");

		purchaseMenu.getChoiceFromOptions(options);

		String expected = "\r\n" + options.get(0).getLocation() + " SOLD OUT!" + "\r\n" + 
				options.get(1).getLocation() + " " + options.get(1).getName() + " $" + options.get(1).getPrice() + "\r\n" + 
				options.get(2).getLocation() + " " + options.get(2).getName() + " $" + options.get(2).getPrice() + "\r\n" + 
				options.get(3).getLocation() + " " + options.get(3).getName() + " $" + options.get(3).getPrice() + "\r\n\n" + "Please choose an option >>> ";


		
		Assert.assertEquals(expected, output.toString());
	}

	private PurchaseMenu getPurchaseMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new PurchaseMenu(input, output);
	}

	private PurchaseMenu getPurchaseMenuForTesting() {
		return getPurchaseMenuForTestingWithUserInput("A1\n");
	}

}
