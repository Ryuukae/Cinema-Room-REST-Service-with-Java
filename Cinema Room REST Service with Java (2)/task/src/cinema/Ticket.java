package cinema;

import java.util.logging.Logger;

public class Ticket {
	// Create a logger for this class
	private static final Logger LOGGER = Logger.getLogger(Ticket.class.getName());

	// Variables to hold the row, column, and price of the ticket
	private int row;
	private int column;
	private int price;

	// Constructor
	public Ticket(int row, int column, int price) {
		// Adding +1 to input row and column to follow one-based numbering convention
		this.row = row + 1;
		this.column = column + 1;
		this.price = price;

		// Debugging and logging the creation of a ticket
		LOGGER.info(String.format("Created ticket at row %s, column %s with price %s", this.row, this.column, this.price));
	}

	// Getter for row
	public int getRow() {
		// Debugging and logging the get operation
		LOGGER.info(String.format("Get row: %s", this.row));
		return row;
	}

	// Getter for column
	public int getColumn() {
		// Debugging and logging the get operation
		LOGGER.info(String.format("Get column: %s", this.column));
		return column;
	}

	// Getter for price
	public int getPrice() {
		// Debugging and logging the get operation
		LOGGER.info(String.format("Get price: %s", this.price));
		return price;
	}
}
