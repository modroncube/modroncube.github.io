import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CPT121 Programming 1
 * RMIT University
 * Study Period 2, 2019
 * Assignment 1
 * Invoice.java
 * 
 * @author Michael Seymour - s3040138
 * 
 * michael.seymour@student.rmit.edu.au
 *
 */

/**
 * Invoice data class
 * 
 * This Class provides a template for use with the accompanying Inventory and
 * SalesSystem classes
 * 
 * In addition to standard accessor and mutator methods, it provides methods to:
 * 
 * - retrieve the local date and time of wherever the invoice is instantiated
 * - build an invoice header as a String with a unique invoice number and customer information
 * - build a String of each purchase with item and quantity information
 * - calculate appropriate delivery costs according to user Input
 * - return a complete invoice String of all compiled information including a 
 *   header, customer information, delivery information, purchase details and
 *   subtotal & total invoice costs
 * 
 */
public class Invoice
{
	/**
	 * delivery fees for:
	 * Australia
	 * New Zealand
	 * USA
	 * In-store pick-up [no charge]
	 */
	private final double[] DELIVERYFEES = { 9.95,
											20.00,
											37.96,
											0.00 };
	private final double INSURANCE = 9.95;
	 
	// All vital private customer and invoice information necessary for each unique invoice
	private String customerName,
				   contactNumber,
				   streetAddress,
				   city,
				   country,
				   
				   purchaseString,
				   dateTime,
				   invoiceNumber;
	
	// The price total of the invoice is declared here as a global variable
	private double totalOrderCost;
	
	
	/**
	 * Constructs a new Invoice object containing the current customer's name,
	 * contact number and street address
	 * 
	 * NOTE: The constructor also initialises the global String purchaseString,
	 * 		 get the local system's date and time as the moment the Invoice is
	 * 		 instantiated, and generates a unique random invoice number
	 * 
	 * @param customerName
	 * 				The name of the customer of the current invoice
	 * 
	 * @param contactNumber
	 * 				The contact number of the customer of the current invoice
	 * 
	 * @param streetAddress
	 * 				The street address of the customer of the current invoice
	 */
	public Invoice(String customerName, String contactNumber, String streetAddress)
	{
		this.customerName = customerName;
		this.contactNumber = contactNumber;
		this.streetAddress = streetAddress;
		
		this.purchaseString = "";
		this.dateTime = getDateTime();
		// generate a unique random invoice number with every new invoice instance
		this.invoiceNumber = Integer.toString((int)(Math.random() * 1234567890));
	}
	
	
	/**
	 * Utilises the LocalDateTime class to get the local date and time information 
	 * from the user's system, and applies the DateTimeFormatter class to present
	 * the information as "day/month/year hour:minute"
	 * 
	 * @return formatter.format(ldt)
	 * 				The user's local date and time as day/month/year hour:minute
	 * 				stored in the global variable dateTime [see class constructor]
	 */
	public String getDateTime()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
		LocalDateTime ldt = LocalDateTime.now();
		return formatter.format(ldt);
	}
	
	
	/**
	 * Returns the index of the country/delivery location of the customer 
	 * of the invoice
	 * 
	 * @param country
	 * 			the country/delivery location for which an index is required
	 * 
	 * @return index position for corresponding country/delivery location if
	 * 			found, or -1 if specified country/location was not found.
	 */
	public int getCountryIndex(String country)
	{
		// convert all strings to lower case for condition checking
		country = country.toLowerCase();
		switch(country)
		{
			case "australia":
				return 0;
			case "new zealand":
				return 1;
			case "usa":
				return 2;
			case "in-store pick-up":
				return 3;
			default:
				return -1;
		}
	}
	

	/**
	 * Returns the current String of purchases for the current invoice
	 * 
	 * @return purchaseString
	 * 				The global variable String which contains all purchases
	 * 				made for the current invoice
	 */
	public String getPurchaseString()
	{
		return purchaseString;
	}
	
	
	/**
	 * Sets the cutomer's country/delivery option from SalesSystem class menu
	 * 
	 * @param country
	 * 				The country/delivery location
	 * 
	 * @return purchaseString
	 * 				The global variable String which contains all purchases
	 * 				made for the current invoice
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	
	/**
	 * Sets the cutomer's city from SalesSystem class menu
	 * This method is used if the user selects a country (not
	 * the 'in-store pick-up') delivery location
	 * 
	 * @param city
	 * 				The customer's city for delivery
	 * 
	 * @return city
	 * 				The global variable String city
	 */
	public void setCity(String city)
	{
		this.city = city;
	}
	
	
	/**
	 * Updates the global String purchaseString with items for recording on the current invoice
	 * 
	 * @param inventory
	 * 				The Inventory object which contains specific item information required
	 * 				for recording (item description and price)
	 *
	 * @param productID
	 * 				The selected product ID taken from user input
	 * 
	 * @param productQuantity
	 * 				Selected number of items for purchase, taken from user input
	 * 
	 */
	public void addPurchaseItem(Inventory inventory, String productID, int productQuantity)
	{
		// calculate subtotal of current item/s
		double subtotal = (inventory.getPrice(productID) * productQuantity);
		// add this subtotal to overall invoice price total
		totalOrderCost += subtotal;
				
		// append item/s information to invoice global variable String purchaseString
		purchaseString += "ITEM: " + productID + " " + inventory.getDescription(productID) + "\n"
					    + "PRICE: $" + String.format("%.2f", inventory.getPrice(productID)) + "\n"
   					    + "QUANTITY: " + productQuantity + "\n"
	 				    + "SUBTOTAL: $" + String.format("%.2f", subtotal) + "\n\n";
	}
	
	
	/**
	 * Creates invoice header information from customer information taken
	 * from user input and formats this with the current system date/time and 
	 * a randomly generated invoice number
	 * 
	 * @return invoiceHeaderInfo
	 * 				String to contain all known customer and invoice information
	 */
	public String makeInvoiceHeaderInfo()
	{
		// string to build delivery location information (to append to header String)
		String cityCountry = "";
		
		if (city == null && country == null)
		// user is previewing invoice - no delivery information
		{
			cityCountry = "[Delivery information pending]";
		}
		else if (city.equals(" ") && country != null)
		// user has selected in-store pick-up delivery - no city information
		{
			cityCountry = country;
		}
		else
		// normal delivery information - city and country
		{
			cityCountry = city + ", " + country;
		}
		
		// return all known customer and invoice information (without purchases) to the header String
		
		return "TOY UNIVERSE\n"
			   + "INVOICE NUMBER: INV-" + invoiceNumber + "\n\n"
			   + dateTime + "\n\n"
			   + "CUSTOMER INFORMATION:\n"
			   + customerName + "\n"
			   + contactNumber + "\n"
			   + streetAddress + "\n"
			   + "DELIVERY TO:\n"
			   + cityCountry + "\n";
	}
	
	
	/**
	 * Calculate the appropriate delivery cost
	 * 
	 * @return deliveryCost
	 * 				Calculated delivery cost formatted as a string for printing	
	 */
	public String calcDeliveryCost()
	{
		return "DELIVERY FEE: " + country.toUpperCase() + " $"
		+ String.format("%.2f", DELIVERYFEES[getCountryIndex(country)]) + "\n";
	}
	
	
	/**
	 * 
	 * Collate all the collected information into the final invoice
	 * 
	 * @param insurance
	 * 				Boolean value for whether to add insurance or not
	 * 
	 * @param preview
	 * 				Boolean value to determine whether this method is being called to
	 * 				preview the invoice information, or it is the final print (necessary
	 * 				for adding or omitting insurance/delivery information)
	 * 
	 * @return invoice
	 * 				The final invoice String which consists of header information,
	 * 				delivery information, purchase information, and the total cost
	 */
	public String printInvoice(boolean insurance, boolean preview)
	{
		String totalOrderString = "";
		
		// dividers for presentation
		String starDivider = "**********************************************************************";
		String lineDivider = "----------------------------------------------------------------------";
		
		if (insurance == true && preview == false)
		// final invoice calculations - no insurance
		{
			totalOrderString += calcDeliveryCost();
			totalOrderCost += DELIVERYFEES[getCountryIndex(country)] + INSURANCE;
			totalOrderString += "INSURANCE FEE: $9.95\n"
							 + "TOTAL ORDER: $" + String.format("%.2f", totalOrderCost) + "\n";
		}
		else if (insurance == false && preview == false)
		// final invoice calculations - with insurance
		{
			totalOrderString += calcDeliveryCost();
			totalOrderCost += DELIVERYFEES[getCountryIndex(country)];

			totalOrderString += "\nTOTAL ORDER: $" + String.format("%.2f", totalOrderCost) + "\n";
		}
		else if (insurance == false && preview == true)
		// preview of invoice from the main menu - no insurance or delivery info
		{
			totalOrderCost += 0.00;
			totalOrderString = "TOTAL ORDER: $" + String.format("%.2f", totalOrderCost) + "\n";
		}
		return starDivider + "\n"
			   + makeInvoiceHeaderInfo()
			   + "\n" + lineDivider + "\n"
			   + purchaseString
			   + lineDivider + "\n"
			   + totalOrderString + "\n"
			   + starDivider + "\n";
	}
}
