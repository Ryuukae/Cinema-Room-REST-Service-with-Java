package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ticket {
	private static final Logger logger = LoggerFactory.getLogger(Ticket.class);

	private int row;
	private int column;
	private int price;

	public Ticket(int row, int column, int price) {
		this.row = row + 1;
		this.column = column + 1;
		this.price = price;

		logger.debug("Ticket created: row {}, column {}, price {}", row, column, price);
	}

	public int getRow() {
		logger.debug("Getting row: {}", row);
		return row;
	}

	public int getColumn() {
		logger.debug("Getting column: {}", column);
		return column;
	}

	public int getPrice() {
		logger.debug("Getting price: {}", price);
		return price;
	}
}
