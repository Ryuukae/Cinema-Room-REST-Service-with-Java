package cinema;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Getter
@Setter
public class Seat {

	// Generate a Universally Unique Identifier(UUID) for each seat
	private UUID token;

	// Seat Booking status. Default is false which means it's not booked
	private boolean booked;

	// Pricing details for the seat
	private int price;

	// The row number of the seat
	private int row;

	// The column number of the seat
	private int column;

	// The ticket generated when the seat is booked
	private Ticket ticket;

	// Logger for Seat class for debugging and logging
	private static final Logger LOGGER = LoggerFactory.getLogger(Seat.class);

	// Constructor for Seat class
	public Seat(int row, int column) {
		// Log the creation of a new seat with its row and column details
		LOGGER.debug("Creating new seat with row: {} and column: {}", row, column);

		// Assign a new UUID for each new seat
		this.token = UUID.randomUUID();
		LOGGER.debug("Assigned Token: {}", this.token);

		this.booked = false;
		LOGGER.debug("Initial booked status set to: {}", this.booked);

		this.row = row;
		LOGGER.debug("Set row to: {}", this.row);

		this.column = column;
		LOGGER.debug("Set column to: {}", this.column);
	}

	// get and set methods for the fields of Seat class

	// Method to assign a price to the seat
	public void assignPrice(Seat seat) {
		LOGGER.debug("Assigning price to seat at row {} column {}", seat.getRow(), seat.getColumn());

		// Seats in the first 3 rows are priced differently from the others
		if (seat.getRow() <= 3) {
			seat.setPrice(10);
		} else {
			seat.setPrice(8);
		}
//         Log the price assigned to the seat
		LOGGER.debug("price: {}", seat.getPrice());
	}

	// Method to initialize the Tickets for a seat
	public void initializeTickets(Seat seat) {
		LOGGER.debug("Initializing tickets for seat at row {} column {}", seat.getRow(), seat.getColumn());
		this.ticket = new Ticket(seat.getRow(), seat.getColumn(), seat.getPrice());
		// Log the details of the ticket created against a seat
		LOGGER.debug("Ticket created with row: {}, column: {}, price: {}", ticket.getRow(), ticket.getColumn(), ticket.getPrice());
	}
}
