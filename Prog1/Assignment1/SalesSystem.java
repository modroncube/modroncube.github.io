import java.util.*;

/**
 * CPT121 Programming 1
 * RMIT University
 * Study Period 2, 2019
 * Assignment 1
 * SalesSystem.java
 * 
 * @author Michael Seymour - s3040138
 * 
 * michael.seymour@student.rmit.edu.au
 *
 */

/**
 * SalesSystem class to provide menus and get user input for use with
 * the accompanying Invoice and Inventory classes
 * 
 * This Class:
 * - instantiates an Inventory object
 * - instantiates an Invoice object
 * - displays a menu of available items for purchase
 * - gets user input for purchases to add to the current invoice
 * - displays a warning if an Inventory item is low on stock
 * 	 and prompts the user to order more
 *
 */
public class SalesSystem
{
	// Declare the following objects in global scope to be accessed anywhere in this Class
	static Scanner scanner;
	static Inventory inventory;
	static Invoice invoice;
	static SalesSystem sales;
	
	
	/**
	 *  The main program menu displays options for the user to:
	 *  - create a new invoice
	 *  - view current purchase information
	 *  - add an item to the existing invoice
	 *  - exit the program
	 */
	public void mainMenu()
	{
		String menuString = " ";
		char menuChar = ' ';
		String errorString = "ERROR: Please select from options A, B, C or X";
		do
		// Do-While to get options A B C or X
		{
			try
			// Try-Catch as a precaution against accidental empty input
			{
				System.out.print("\nPlease select from the following options:\n\n"
							     + "A: START A NEW INVOICE\n"
							     + "B: VIEW PURCHASE INFORMATION\n"
							     + "C: ADD PURCHASE\n\n"
							     + "X: EXIT\n\n");
				menuString = scanner.nextLine();
				
				// Get first character of the string and ensure it's lower case (for condition checking)
				menuChar = menuString.toLowerCase().charAt(0);
				
				if (menuString.length() == 1)
				{
					if (menuChar == 'a')
					{
						createNewInvoice();
					}
					else if (menuChar == 'b')
					{
						previewInvoice();
					}
					else if (menuChar == 'c')
					{
						showPurchaseMenu();
					}
					else if (menuChar == 'x')
					{
						quit();
					}
					else
					{
						System.out.println(errorString);
					}
				}
				else
				{
					System.out.println(errorString);
				}
			}
			catch (Exception e)
			{
				//System.out.println(errorString);
			}
		}
		// Menu will repeat until there is valid input
		while (( menuChar != 'a' ||
			     menuChar != 'b' ||
			     menuChar != 'c' ||
			     menuChar != 'x')
			    );
	}
	
	
	/**
	 * Presents a menu to get required parameters about the current customer
	 * in order to instantiate a new Invoice object
	 */
	void createNewInvoice()
	{
		// Get initial customer info
		String[] menuString = { "Customer name: ",
								"Customer contact number: ",
								"Customer street address: ", };
		/*
		 *  String to contain all customer information:
		 *  [0] name
		 *  [1] contact number
		 *  [2] street address
		 *  [3] city
		 *  [4] country
		 */
		String[] newCustomer = new String[5];
		System.out.println("Please enter the following information:");
	
		for (int i = 0; i < menuString.length; i++)
		// Print menu strings and get input one line at a time
		{
			do
			// Prevent null input
			{
				System.out.print(menuString[i]);
				newCustomer[i] = scanner.nextLine();
			}
			while (newCustomer[i].isEmpty());
		}

		/*
		 * Invoice is constructed with:
		 * - customer name
		 * - customer contact number
		 * - customer street address
		 */
		invoice = new Invoice( newCustomer[0],
							   newCustomer[1],
							   newCustomer[2]);
		System.out.println("\nSuccessfully created new invoice\n");
	}

			
	/**
	 * Preview the current invoice or warn user to first create an invoice
	 * NOTE: preview will not contain delivery or insurance information as this
	 * is only collected when finalising the invoice after purchasing is complete
	 */
	void previewInvoice()
	{
		if (invoice == null)
		// Ensure a current invoice exists
		{
			System.out.println("\nPlease create a new invoice first.\n");
		}
		else
		// Print the invoice as it currently exists
		{
			// Params indicate "do not add insurance" and "this is a preview, not the the final invoice"
			System.out.print(invoice.printInvoice(false, true));
		}
	}
	
	
	/**
	 * Display the items available for purchase to the user including
	 * the product ID, item description, price and current quantity in
	 * stock (of the Inventory object)
	 */
	void showPurchaseMenu()
	{
		String lineDivider = "----------------------------------------------------------------------";
		if (invoice == null)
		// Ensure a current invoice exists
		{
			System.out.println("\nPlease create a new invoice first.\n");
		}
		else
		{
			// Get product IDs from Inventory for ease of use
			String[] allProductIDs = inventory.getAllProductIDs();
			String selectedProductID = "";
			
			// Print menu headers
			System.out.println("\n" + lineDivider);
			System.out.printf("%-1s |      %-25s |  %-7s| %-1s",
							  "Product ID",
							  "Product Description",
							  "Price",
							  "Current Stock\n");
			for (int i = 0; i < 2; i++)
			// Print a double line divider
			{
				System.out.println(lineDivider);
			}
			for (int i = 0; i < allProductIDs.length ; i++)
			// Populate menu rows/columns with Inventory info
			{
				System.out.printf("     %-5s | %-"
						         // Dynamically calculate the space required for description row spacing of 30 characters 
								 + ((inventory.getDescription(allProductIDs[i]).length())
								 + (30 - inventory.getDescription(allProductIDs[i]).length()))
								 
								 + "s | $ %-1.2f |       %-1d",
								  allProductIDs[i],
								  inventory.getDescription(allProductIDs[i]),
								  inventory.getPrice(allProductIDs[i]),
								  inventory.getStockLevel(allProductIDs[i])
								  );
				// Row border
				System.out.println("\n" + lineDivider);
			}
			System.out.println("\nP: FINALISE ORDER AND PRINT INVOICE\n");
			
			// Automatically check Inventory stock levels for items which have 0 or 1 item/s left
			sales.checkStockQuantity(allProductIDs);
			
			System.out.println("Please select an item by product ID [or P to print invoice]: ");
			
			// Convert all input to upper case for condition checking
			selectedProductID = scanner.nextLine().toUpperCase();
			if (selectedProductID.equals("P"))
			{
				sales.finaliseOrder();
			}
			else
			// If user selection is not P [print invoice & exit], continue with purchasing sequence
			{
				if (inventory.getStockLevel(selectedProductID) == -1)
				// Check Inventory error code for valid product ID		
				{
					System.out.println("ERROR: Product ID not found.");
					sales.mainMenu();
				}
				else
				{
					makePurchase(selectedProductID);
				}
			}
		}
		// Return to main menu after each purchase sequence
		sales.mainMenu();
	}

	
	/**
	 * Checks the stock quantity of all items in Inventory and warns to users
	 * if the quantity is 1 or fewer, with an option to proceed with ordering
	 * more
	 * 
	 * @param allProductIDs
	 * 				A String array of product IDs from Inventory
	 */
	void checkStockQuantity(String[] allProductIDs)
	{
		String menuString = "";
		char menuChar = ' ';
		
		for (int i = 0; i < allProductIDs.length; i++)
		// Check the stock quantity of each item in Inventory
		{
			int itemStockLevel = inventory.getStockLevel(allProductIDs[i]);
			if (itemStockLevel <= 1)
			// Alert the user if stock quantity of any item is 1 or fewer
			{
				do
				{
					try
					{
						System.out.println("ATTENTION: item " 
								+ allProductIDs[i] + " " + inventory.getDescription(allProductIDs[i])
								+ " has " + itemStockLevel + " unit/s remaining.\n\n"
								+ "Would you like to order more units? [y/n]");
						menuString = scanner.nextLine();
						menuChar = menuString.toLowerCase().charAt(0);
						
						if (menuChar == 'y')
						{
							sales.reorderStock(allProductIDs[i]);
						}
						else if (menuChar == 'n')
						// End loop and continue with purchasing menu
						{
							break;
						}
						else
						{
							System.out.println("Please choose [y/n]\n");
						}
					}
					catch (Exception e)
					{
						System.out.println("Please choose [y/n]\n");	
					}
					
				} while (menuChar != 'y' || menuChar != 'n');
			}
		}
	}
	
	
	/**
	 * Displays a menu to order more stock of a selected Inventory item
	 * 
	 * @param selectedProductID
	 * 					The user's selected Inventory product ID
	 */
	void reorderStock(String selectedProductID)
	{
		int reorderQuantity = 0;
		do
		{
			try
			{
				System.out.println("How many units of "
						+ selectedProductID + " "
						+ inventory.getDescription(selectedProductID)
						+ " would you like to order?");
				reorderQuantity = scanner.nextInt();
	
				// Contain order request in a variable for legibility
				int checkOrderValid = inventory.orderStock(selectedProductID, reorderQuantity);
				
				if (checkOrderValid > 0)
				// If the quantity and product ID are valid, reorder stock
				{
					inventory.orderStock(selectedProductID, 0);
					System.out.println("Successfully reordered " + reorderQuantity
										+ " unit/s of " + selectedProductID + " "
										+ inventory.getDescription(selectedProductID));
				}
				else if (checkOrderValid == -1)
				// Check for valid product ID
				{
					System.out.println("ERROR: Product ID not found.");
				}
				else if (checkOrderValid == -2)
				// Check for valid order quantity
				{
					System.out.println("ERROR: Order quantity must be greater than 0.");
				}	
			}
			catch (Exception e)
			{
				System.out.println("Please enter a number");
			}
			// Consume trailing line
			scanner.nextLine();
		}
		while (reorderQuantity < 1);
		sales.showPurchaseMenu();
	}
	
	
	/**
	 * This method adds purchased item/s (in a user determined quantity )to
	 * the current invoice and removes the same quantity of item/s from the
	 * Inventory object.
	 * 
	 * Checks are performed utilising the Inventory class error codes to ensure
	 * the given product ID and quantity are valid, prompting the user to order more
	 * items if the quantity in stock is insufficient
	 * 
	 * @param selectedProductID
	 * 					The  user designated product to purchase and add to the invoice
	 */
	void makePurchase(String selectedProductID)
	{		
		// Get number of units to order
		System.out.println("How many units of "
						 + inventory.getDescription(selectedProductID)
						 + " would you like to order?");
		int orderQuantity = scanner.nextInt();
		// Consume trailing line
		scanner.nextLine();
		
		//Inventory class error checking
		if (inventory.removeStock(selectedProductID, orderQuantity) >= 0)
		// If product ID and requested quantity are valid, proceed with purchase
		{
			inventory.removeStock(selectedProductID, 0);
			invoice.addPurchaseItem(inventory, selectedProductID, orderQuantity);
			
			// Confirm order to the user
			System.out.println("\nOrder for " + orderQuantity	
								+ " unit/s of "	+ selectedProductID + " " 
								+ inventory.getDescription(selectedProductID) + " confirmed.\n");
		}
		else if (inventory.removeStock(selectedProductID, orderQuantity) == -1)
		// Check against invalid product ID
		{
			System.out.println("ERROR: Product ID not found.");
			sales.mainMenu();
		}
		else if (inventory.removeStock(selectedProductID, orderQuantity) == -2)
		// Check against invalid quantity from user
		{
			System.out.println("ERROR: Order quantity must be greater than 0.");
		}
		else if (inventory.removeStock(selectedProductID, orderQuantity) == -3)
		// Check against insufficient quantity of items in Inventory
		{
			// Variables for user confirmation to order more items (if order quantity is
			// more than available stock)
			String reorderString = "";
			char reorderChar = ' ';
			
			// Notify the user of the error
			System.out.println("ATTENTION: Could not complete order for "
								+ orderQuantity	+ " unit/s of "	+ selectedProductID + " " 
								+ inventory.getDescription(selectedProductID)
								+ "- Insufficient items in stock\n");
			// Prompt user to order more units of their selected item
			do
			{
				try
				{
					System.out.println("Would you like to order more units of "
							+ selectedProductID + " " 
							+ inventory.getDescription(selectedProductID) + "? [y/n]");
					reorderString = scanner.nextLine();
					reorderChar = reorderString.toLowerCase().charAt(0);
					
					switch (reorderChar)
					{
					case 'y':
					// Continue with reordering stock
						reorderStock(selectedProductID);
						break;
					case 'n':
					// Abandon purchase
						System.out.println("Order for "+ orderQuantity
											+ " unit/s of "	+ selectedProductID + " " 
											+ inventory.getDescription(selectedProductID)
											+ " cancelled");
						break;
					default:
						System.out.println("Please choose [y/n]\n");	
					}
				}
				catch (Exception e)
				{
					System.out.println("Please choose [y/n]\n");	
				}
			}
			while (reorderChar != 'y' && reorderChar != 'n');
		}
		sales.mainMenu();
	}
	
	
	/**
	 * Get confirmation that the user wishes to finish purchasing and finalise the 
	 * order, print the final invoice, and then exit the program
	 * 
	 */
	void finaliseOrder()
	{
		// Show a preview of the current invoice
		System.out.println("Your current invoice item/s:\n\n" + invoice.getPurchaseString());
		int finaliseMenuOption = 0;
		int insuranceOption = 0;
		do
		{
			try
			{
				System.out.println("1: Confirm order\n2: Return to Main Menu");	
				finaliseMenuOption = scanner.nextInt();
				
				// Consume trailing line
				scanner.nextLine();
				if (finaliseMenuOption == 1)
				{
					showDeliveryMenu();
					insuranceOption = showInsuranceMenu();
				}
				else if (finaliseMenuOption == 2)
				{
					System.out.println("Confirmation cancelled");
				}
				else
				{
					System.out.println("Please select between options 1 and 2\n");
					// Consume trailing line
					scanner.nextLine();
				}
			}
			catch (Exception e)
			{
				System.out.println("Please select between options 1 and 2\n");
				// Consume trailing line
				scanner.nextLine();
			}
		}
		while (finaliseMenuOption < 1 || finaliseMenuOption > 2);
		
		// After getting insurance information, print final invoice
		switch (insuranceOption)
		{
		case 1:
			System.out.print(invoice.printInvoice(true, false));
			quit();
			case 2:
			System.out.print(invoice.printInvoice(false, false));
			quit();
		}
	}
	
	
	/**
	 * A simple menu to get input on which country/city for delivery, or in-store pick-up,
	 * and modify the String for the final invoice accordingly
	 */
	void showDeliveryMenu()
	{
		int countryOption = 0;
		do
		{
			try
			{
				// Menu to select a country from limited options
				System.out.println("\nSelect a country for delivery:\nNOTE: Deliveries can be made only to the following countries:\n\n"
								   + "1: Australia\n"
								   + "2: New Zealand\n"
								   + "3: USA\n"
								   + "4: In-store pick-up (no delivery fee)");
				countryOption = scanner.nextInt();
				switch (countryOption)
				{
				case 1:
					invoice.setCountry("Australia");
					break;
				case 2:
					invoice.setCountry("New Zealand");
					break;
				case 3:
					invoice.setCountry("USA");
					break;
				case 4:
					invoice.setCountry("In-store pick-up");
					break;
				default:
					System.out.println("Please select from options 1 - 4.");
				}
			}
			// prevent non-integer input
			catch (Exception e)
			{
				System.out.println("Please select from options 1 - 4.");
			}
			// Consume trailing line
			scanner.nextLine();
		}
		while (countryOption < 1 || countryOption > 4);

		if (countryOption != 4)
		// Get city information if not in-store pick-up
		{
			String city = "";
			do
			// Prevent null input
			{
				System.out.println("Please enter the city to deliver to: ");
				city = scanner.nextLine();
			}
			while (city.isEmpty());			
			invoice.setCity(city);
		}
		else
		// In-store pick-up: city doesn't show on invoice
		{
			invoice.setCity(" ");
		}
	}
	
	
	/**
	 * A simple menu for input on whether to add insurance to the invoice
	 * 
	 * @return insuranceOption
	 * 					An integer to represent the final delivery/insurance combination to
	 * 					print on the final invoice
	 */
	int showInsuranceMenu()
	{
		String addInsuranceString = "";
		char addInsuranceChar = ' ';
		int insuranceOption = 0;
		do
		{
			try
			{
				System.out.println("Would you like to add insurance for $9.95 [y/n]?\n");
				addInsuranceString = scanner.nextLine();
				addInsuranceChar = addInsuranceString.toLowerCase().charAt(0);
				
				switch (addInsuranceChar)
				{
				case 'y':
					insuranceOption = 1;
					break;
				case 'n':
					insuranceOption = 2;
					break;
				default:
					System.out.println("Please choose [y/n]\n");
				}
			}
			catch (Exception e)
			{
				System.out.println("Please choose [y/n]\n");
			}
		}
		while (addInsuranceChar != 'y' && addInsuranceChar != 'n');
		return insuranceOption;
	}
	
	
	/**
	 * Quit the program
	 */
	void quit()
	{
		System.out.println("Thank you for using SalesSystem");
		scanner.close();
		System.exit(0);
	}
	
	/**
	 * The main program method instantiates the Scanner and Inventory objects, then
	 * instantiates itself (calling its own constructor to display the main menu)
	 */
	public static void main(String[] args)
	{
		// instantiate objects
		scanner = new Scanner(System.in);
		inventory = new Inventory("TOY UNIVERSE");
		sales = new SalesSystem();
		
		sales.mainMenu();
	}
}
