package cinema;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Getter
@Setter
public class Seat {
	private UUID token;
	private boolean booked;
	private int price;
	private int row;
	private int column;
	private Ticket ticket;
	private static final Logger LOGGER = LoggerFactory.getLogger(Seat.class);

	public Seat(int row, int column) {

		LOGGER.debug("Creating new seat with row: {} and column: {}", row, column);

		this.token = UUID.randomUUID();

		LOGGER.debug("Assigned Token: {}", this.token);

		this.booked = false;

		LOGGER.debug("Initial booked status set to: {}", this.booked);

		this.row = row;

		LOGGER.debug("Set row to: {}", this.row);

		this.column = column;

		LOGGER.debug("Set column to: {}", this.column);
	}

	public UUID getToken() {
		LOGGER.debug("Getting Token: {}", this.token);
		return this.token;
	}

	public void setToken(UUID token) {
		LOGGER.debug("Setting Token: {}", token);
		this.token = token;
	}

	public boolean isBooked() {
		LOGGER.debug("Checking if the seat is booked: {}", this.booked);
		return this.booked;
	}

	public void setBooked(boolean booked) {
		LOGGER.debug("Changing the booking status of the seat to: {}", booked);
		this.booked = booked;
	}

	public int getPrice() {
		LOGGER.debug("Getting seat price: {}", this.price);
		return this.price;
	}

	public void setPrice(int price) {
		LOGGER.debug("Setting seat price as: {}", price);
		this.price = price;
	}

	public int getRow() {
		LOGGER.debug("Getting seat row: {}", this.row);
		return this.row;
	}

	public void setRow(int row) {
		LOGGER.debug("Setting seat row as: {}", row);
		this.row = row;
	}

	public int getColumn() {
		LOGGER.debug("Getting seat column: {}", this.column);
		return this.column;
	}

	public void setColumn(int column) {
		LOGGER.debug("Setting seat column as: {}", column);
		this.column = column;
	}

	public void assignPrice(Seat seat) {
		LOGGER.debug("Assigning price to seat at row {} column {}", seat.getRow(), seat.getColumn());
		if (seat.getRow() <= 3) {
			seat.setPrice(10);
			LOGGER.debug("Seat price set to: {}", 10);
		} else {
			seat.setPrice(8);
			LOGGER.debug("Seat price set to: {}", 8);
		}
		LOGGER.debug("Final calculated price: {}", seat.getPrice());
	}

	public void initializeTickets(Seat seat) {
		LOGGER.debug("Initializing tickets for seat at row {} column {}", seat.getRow(), seat.getColumn());
		this.ticket = new Ticket(seat.getRow(), seat.getColumn(), seat.getPrice());
		LOGGER.debug("Ticket created with row: {}, column: {}, price: {}", ticket.getRow(), ticket.getColumn(), ticket.getPrice());
	}

	public Ticket getTicket() {
		LOGGER.debug("Getting information about the Ticket for this seat");
		return this.ticket;
	}
}
