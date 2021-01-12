package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseMenu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT , MAIN_MENU_OPTION_SALES};

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_EXIT = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_EXIT };

	private static List<Consumable> products = new ArrayList<>();
	
//	private static BigDecimal money = BigDecimal.valueOf(0.00).setScale(2);
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

	private Menu menu;
	private PurchaseMenu purchaseMenu;
	private PrintWriter logger;

	public VendingMachineCLI(Menu menu, PurchaseMenu purchaseMenu) throws IOException {
		this.menu = menu;
		this.purchaseMenu = purchaseMenu;
		File logFile = new File("Log.txt");
		if (logFile.exists()) {
			logger = new PrintWriter(new FileWriter(logFile, true));
		}
		else {
			logger = new PrintWriter(logFile.getAbsoluteFile());
		}
		logger.println("Vending Machine Started: " + LocalDateTime.now().format(formatter));
	}
	

	public void run() throws IOException {
		
		while (true) {
			System.out.print("Please enter the file path for the vending machine >>> ");
			Scanner userInput = new Scanner(System.in);
			String input = userInput.nextLine();
			File inputFile = new File(input);
			if (!inputFile.exists()) {
				System.out.println("This file doesn't exist.");
				continue;
			}
			try (Scanner fileInput = new Scanner(inputFile)) {
				while(fileInput.hasNext()) {
					String line = fileInput.nextLine();
					String[] lineArray = line.split("\\|");
					BigDecimal price = new BigDecimal(lineArray[2]);
					switch(lineArray[3]) {
					case "Chip": 
						products.add(new Chip(lineArray[0], lineArray[1], price));	
						break;
					case "Candy": 
						products.add(new Candy(lineArray[0], lineArray[1], price));
						break;
					case "Drink": 
						products.add(new Drink(lineArray[0], lineArray[1], price));
						break;
					case "Gum": 
						products.add(new Gum(lineArray[0], lineArray[1], price));
					}
				}
				//userInput.close();
				break;
			}
		}
			
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS, 1);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				viewProducts();// display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				runPurchase();// do purchase
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Thanks for shopping with us!");
				break;
			} else if (choice.equals(MAIN_MENU_OPTION_SALES)) {
				salesReport();
			}
		}
		logger.println("Vending Machine stopped at: " + LocalDateTime.now().format(formatter) + "\n");
		logger.flush();
		logger.close();
	}

	private void salesReport() throws IOException {
		logger.println(LocalDateTime.now().format(formatter) + " Sales Report Generated");
		BigDecimal totalSales = new BigDecimal("0.00").setScale(2);
		File salesFile = new File("SalesReport.txt");
		salesFile.createNewFile();
		PrintWriter salesLogger = new PrintWriter(salesFile.getAbsoluteFile());
		salesLogger.println("Sales Report: " + LocalDateTime.now().format(formatter));
		for (Consumable product : products) {
			int sold = 5 - product.getQuantity();
			totalSales = totalSales.add(product.getPrice().multiply(new BigDecimal(sold)));
			salesLogger.println(product.getName() + "|" + sold);
		}
		salesLogger.println("***TOTAL SALES***\n$" + totalSales);
		salesLogger.flush();
		salesLogger.close();
	}

	
	public void runPurchase() {
		BigDecimal money = BigDecimal.valueOf(0.00).setScale(2);
		while (true) {
			//System.out.println("Current money provided: $" + money);
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS, money);
			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				while (true) {
					System.out.print("Please enter a bill ($1, $2, $5 or $10 bills only please) >> ");
					Scanner userInput = new Scanner(System.in);
					try  {
						int bill = Integer.parseInt(userInput.nextLine());
						money = feedMoney(money, bill);// display vending machine items
						System.out.println("Current money provided: $" + money);
						System.out.print("Do you wish to keep adding money? Y/N >> ");
						String cont = userInput.nextLine();
//						System.out.println("Current money provided: $" + money);
						if (cont.toLowerCase().equals("n")) {
							break;
						}
					}
					catch (InputMismatchException e) {
						System.out.println("Please enter a valid bill");
					}
				}
			} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				money = selectProduct(money, products);// do purchase
			} else if(choice.equals(PURCHASE_MENU_OPTION_EXIT)) {
				System.out.println("From $" + money + ", your change is " + getChange(money) + "\n");
				money = BigDecimal.valueOf(0.00).setScale(2);				
				logger.println(money);
				break;
			}
		}
	}
	
	public String getChange(BigDecimal money) {
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		int pennies = 0;
		
		logger.print(LocalDateTime.now().format(formatter) + " GIVE CHANGE: $" + money + " $");
		
		while (money.compareTo(new BigDecimal("0.24")) == 1) {
			money = money.subtract(new BigDecimal("0.25"));
			quarters++;
		}
		while (money.compareTo(new BigDecimal("0.09")) == 1) {
			money = money.subtract(new BigDecimal("0.10"));
			dimes++;
		}
		while (money.compareTo(new BigDecimal("0.04")) == 1) {
			money = money.subtract(new BigDecimal("0.05"));
			nickels++;
		}
		while (money.compareTo(new BigDecimal("0.00")) == 1) {
			money = money.subtract(new BigDecimal("0.01"));
			pennies++;
		}
		String output = "";
		if (quarters > 0) {
			output += quarters + " quarter";
		}
		if (quarters > 1) {
			output += "s";
		}
		if (dimes > 0 && output.length() == 0) {
			output += dimes + " dime";
		}
		else if(dimes > 0) {
			output += ", " + dimes + " dime";
		}
		if (dimes > 1) {
			output += "s";
		}
		if (nickels > 0 && output.length() == 0) {
			output += nickels + " nickel";
		}
		else if(nickels > 0) {
			output += ", " + nickels + " nickel";
		}
		if (nickels > 1) {
			output += "s";
		}
		if (pennies > 0 && output.length() == 0) {
			output += pennies + " penn";
		}
		else if(pennies > 0) {
			output += ", " + pennies + " penn";
		}
		if (pennies > 1) {
			output += "ies";
		}
		else if (pennies == 1) {
			output += "y";
		}
		return output;
	}
	
	
	public BigDecimal feedMoney(BigDecimal money, int bill) {
		if (bill != 1 && bill != 2 && bill != 5 && bill != 10 && bill != 0) { // add Math.random() < 0.05 for additional realism
			System.out.println("Could not accept bill. Please try again!");
		}
		else {
			money = money.add(BigDecimal.valueOf(bill));
			logger.println(LocalDateTime.now().format(formatter) + " FEED MONEY: $" + bill + ".00 $" + money);
		}
		return money;
	}
	

	public void viewProducts() {
		for (Consumable product : products) {
			if (product.getQuantity() == 0) {
				System.out.println(product.getLocation() + " SOLD OUT");
			} else {
			System.out.println(product.getLocation() + " " + product.getName() + " (" + product.getQuantity() + " left)");
			}
		}
	}
	
	public BigDecimal selectProduct(BigDecimal money, List<Consumable> products) {
		int choice = (Integer) purchaseMenu.getChoiceFromOptions(products);
		
		
		if (choice < 0) {
			System.out.println("That product is sold out!");
		}
		else if (products.get(choice).getPrice().compareTo(money) <= 0) {
			products.get(choice).sold();
			logger.print(LocalDateTime.now().format(formatter) + " " + products.get(choice).getName() + " " + products.get(choice).getLocation() + " $" + money + " $");
			money = money.subtract(products.get(choice).getPrice());
			System.out.println("Dispensing " + products.get(choice).getName() + " for $" + products.get(choice).getPrice());
			System.out.println(products.get(choice).message());
			logger.println(money);
		}
		else {
			System.out.println("You haven't put in enough money for that!");
		}
		return money;
	}
	
	public static void main(String[] args) throws IOException {
		PurchaseMenu purchaseMenu = new PurchaseMenu(System.in, System.out);
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu, purchaseMenu);
		cli.run();
		
	}
}
