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
		return this.token;
	}

	public void setToken(UUID token) {
		this.token = token;
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

	public void assignPrice(Seat seat) {
		LOGGER.debug("Assigning price to seat at row {} column {}", seat.getRow(), seat.getColumn());
		if (seat.getRow() <= 3) {
			seat.setPrice(10);
		} else {
			seat.setPrice(8);
		}
		LOGGER.debug("price: {}", seat.getPrice());
	}

	public void initializeTickets(Seat seat) {
		LOGGER.debug("Initializing tickets for seat at row {} column {}", seat.getRow(), seat.getColumn());
		this.ticket = new Ticket(seat.getRow(), seat.getColumn(), seat.getPrice());
		LOGGER.debug("Ticket created with row: {}, column: {}, price: {}", ticket.getRow(), ticket.getColumn(), ticket.getPrice());
	}

	public Ticket getTicket() {
		return this.ticket;
	}
}
