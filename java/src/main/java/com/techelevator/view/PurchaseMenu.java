package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.techelevator.Consumable;

public class PurchaseMenu{

	private PrintWriter out;
	private Scanner in;

	public PurchaseMenu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	
	public Object getChoiceFromOptions(List<Consumable> options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	
	private Object getChoiceFromUserInput(List<Consumable> options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			for (int i = 0; i < options.size(); i++) {
				if (options.get(i).getLocation().toLowerCase().equals(userInput.toLowerCase())) {
					choice = i;
				}
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		else if (options.get((Integer) choice).getQuantity() == 0) {
//			System.out.println("Sorry, sold out!");
			choice = -1;
		}
		return choice;
	}

	private void displayMenuOptions(List<Consumable> options) {
		out.println();
		for (Consumable option : options) {
			
			if (option.getQuantity() == 0) {
				out.println(option.getLocation() + " SOLD OUT!");
			}
			else {
				out.println(option.getLocation() + " " + option.getName() + " $" + option.getPrice());
			}
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	
	}
}
