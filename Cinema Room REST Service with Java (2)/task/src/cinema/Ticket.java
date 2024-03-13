package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ticket {

	// Define a logger instance specific to this class
	private static final Logger logger = LoggerFactory.getLogger(Ticket.class);

	// Declare variables for row, column and price respectively
	private final int row;
	private final int column;
	private final int price;

	// Constructor that sets the row, column, and price of the ticket
	// It also logs a debug statement with the ticket details
	public Ticket(int row, int column, int price) {
		this.row = row + 1; // Adding 1 because seat numbering usually starts from 1 not from 0
		this.column = column + 1; // Similar to row

		this.price = price; // Ticket price.

		logger.debug("Ticket created: row {}, column {}, price {}", this.row, this.column, this.price); // Debug log statement
	}

	// Getter for row.
	// Logs a debug message detailing the row value it will return
	public int getRow() {
		logger.debug("Getting row: {}", row); // Debug log statement
		return row;
	}

	// Getter for column
	// Logs a debug message detailing the column value it will return
	public int getColumn() {
		logger.debug("Getting column: {}", column); // Debug log statement
		return column;
	}

	// Getter for price.
	// Logs a debug message detailing the price value it will return
	public int getPrice() {
		logger.debug("Getting price: {}", price); // Debug log statement
		return price;
	}
}
