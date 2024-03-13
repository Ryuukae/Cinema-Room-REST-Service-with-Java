package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Cinema {

	private static final Logger LOGGER = LoggerFactory.getLogger(Cinema.class);

	// Cinema layout represented as a 2-d array of Seats
	private final Seat[][] seats;

	// Dimensions of the Cinema layout.
	private final int rows = 9;
	private final int columns = 9;

	// Accounting fields for cinema
	private int income;
	private int availableSeats;
	private int totalPurchasedTickets;

	public Cinema() {
		LOGGER.info("Constructing Cinema. Rows: {}, Columns: {}", rows, columns);

		seats = new Seat[ rows ][ columns ];

		// Initialize the seats in the cinema
		initializeSeats();

		// Assign a price and initial tickets to each seat
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Seat seat = seats[ i ][ j ];
				seat.assignPrice(seats[ i ][ j ]);
				seat.initializeTickets(seats[ i ][ j ]);
			}
		}
	}

	// Get the Cinema's total income.
	public int getIncome() {
		LOGGER.debug("Getting total income");
		return income;
	}

	// Get the number of tickets/seats yet to be purchased.
	public int getAvailableSeats() {
		LOGGER.debug("Getting available seats");
		return availableSeats;
	}

	// Get the total number of purchased tickets.
	public int getTotalPurchasedTickets() {
		LOGGER.debug("Getting total purchased tickets");
		return totalPurchasedTickets;
	}

	// Initialize seats, and set availableSeats to total number of seats.
	private void initializeSeats() {
		// Set the initial availableSeats number
		availableSeats = rows * columns;

		// Create seat objects for each seat
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				seats[ i ][ j ] = new Seat(i, j);
			}
		}
		LOGGER.debug("Seats initialized");
	}

	// Get the Seat associated with a token.
	// If token does not exist, return null.
	public Seat getSeatByToken(String token) {
		LOGGER.debug("Getting seat by token: {}", token);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Seat seat = seats[ i ][ j ];
				if (seat.getToken().equals(token)) {
					return seat;
				}
			}
		}
		LOGGER.debug("Seat not found for token: {}", token);
		return null;
	}

	// Book a Seat if it's not already booked.
	// Update the accounting fields if successful.
	public void bookSeat(Seat seat) {
		LOGGER.debug("Booking seat at Row: {}, Column: {}", seat.getRow(), seat.getColumn());

		if (seat.isBooked()) {
			throw new IllegalStateException("Seat is already booked");
		} else {
			seat.setBooked(true);
			availableSeats--;
			totalPurchasedTickets++;
			income += seat.getPrice();
			LOGGER.debug("Seat at Row: {}, Column: {} booked successfully", seat.getRow(), seat.getColumn());
		}
	}

	// Set the Cinema's total income.
	public void setIncome(int income) {
		LOGGER.debug("Setting income to: {}", income);
		this.income = income;
	}

	// Get all Seats in the Cinema.
	public Seat[][] getSeats() {
		LOGGER.debug("Getting all seats");
		return seats;
	}

	// Get the number of rows in the Cinema.
	public int getRows() {
		LOGGER.debug("Getting row count");
		return rows;
	}

	// Get the number of columns in the Cinema.
	public int getColumns() {
		LOGGER.debug("Getting column count");
		return columns;
	}

	// Get the Seat at the specified row and column.
	public Seat getSeat(int row, int column) {
		LOGGER.debug("Getting seat at Row: {}, Column: {}", row, column);
		return seats[ row ][ column ];
	}
}
