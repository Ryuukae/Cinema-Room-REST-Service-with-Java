package cinema;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class Seat {
	private int idCount = 1;
	private int id;
	private boolean booked;
	private int price;
	private int row;
	private int column;
	private static final Logger LOGGER = LoggerFactory.getLogger(Seat.class);

	public Seat(int row, int column) {

		LOGGER.debug("Creating new seat with row: {} and column: {}", row, column);

		this.id = idCount++;

		LOGGER.debug("Assigned ID: {}", this.id);

		this.booked = false;

		LOGGER.debug("Initial booked status set to: {}", this.booked);

		this.row = row;

		LOGGER.debug("Set row to: {}", this.row);

		this.column = column;

		LOGGER.debug("Set column to: {}", this.column);
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isBooked() {
		return this.booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}


}
