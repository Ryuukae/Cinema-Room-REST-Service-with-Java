package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Cinema {

	// Logger instance used for debugging and logging.
	private static final Logger LOGGER = LoggerFactory.getLogger(Cinema.class);

	// Number of rows and columns for the cinema.
	private final int rows = 9;
	private final int columns = 9;

	// 2D array to hold all the Seats in the cinema.
	private final Seat[][] seats;

	public Cinema() {
		// Logging the initiation of Cinema construction.
		LOGGER.debug("Constructing cinema with {} rows and {} columns", rows, columns);

		// Allocating memory for seats.
		seats = new Seat[ rows ][ columns ];

		// Initialize all the seats.
		initializeSeats();

		// Loop over each seat to assign the price and ticket initialization.
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Seat seat = seats[ i ][ j ];
				// Assign seat price and Initialize ticket.
				seat.assignPrice(seats[ i ][ j ]);
				seat.initializeTickets(seats[ i ][ j ]);

				// Log the successful price assignment and ticket initialization.
				LOGGER.debug("Assigned price and initialized tickets for seat at row {} and column {}", i, j);
			}
		}
	}

	private void initializeSeats() {
		// Log start of initialization.
		LOGGER.debug("Initializing seats");

		// Loop over each place in the 2D array and create a seat instance.
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				seats[ i ][ j ] = new Seat(i, j);

				// Log the successful initialization of a seat.
				LOGGER.debug("Initialized seat at row {} and column {}", i, j);
			}
		}
	}

	// Method to get seat by token.
	public Seat getSeatByToken(String token) {
		// Logging the start of token search.
		LOGGER.debug("Getting seat by token: {}", token);

		// Loop over each seat and check if the token matches.
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getColumns(); j++) {
				Seat seat = seats[ i ][ j ];
				if (seat.getToken().equals(UUID.fromString(token))) {
					// Log successful seat token match.
					LOGGER.debug("Seat found: {}", seat);

					// return the seat.
					return seat;
				}
			}
		}
		// Log that the seat with the token wasn't found.
		LOGGER.debug("Seat not found");

		// Return null if no seat found.
		return null;
	}

	// Method to book a seat.
	void bookSeat(Seat seat) {
		// Log the attempt to book a seat.
		LOGGER.debug("Attempting to book seat at row {} column {}", seat.getRow(), seat.getColumn());

		// Check if the seat is already booked.
		if (seat.isBooked()) {
			// Log that the seat is already booked.
			LOGGER.debug("Seat already booked, throwing exception");
			throw new IllegalStateException("Seat is already booked");
		} else {
			// Book the seat.
			seat.setBooked(true);

			// Log successful seat booking.
			LOGGER.debug("Seat booked successfully");
		}
	}

	// Getter for all seats.
	public Seat[][] getSeats() {
		// Log the attempt to retrieve all seats.
		LOGGER.debug("Getting all seats");

		// Return all seats.
		return seats;
	}

	// Getter for rows.
	public int getRows() {
		// Log the attempt to retrieve the number of rows.
		LOGGER.debug("Getting number of rows");

		// Return number of rows.
		return rows;
	}

	// Getter for columns.
	public int getColumns() {
		// Log the attempt to retrieve the number of columns.
		LOGGER.debug("Getting number of columns");

		// Return number of columns.
		return columns;
	}

	// Getter for a specific seat by row and column.
	public Seat getSeat(int row, int column) {
		// Log the attempt to retrieve the seat.
		LOGGER.debug("Getting seat at row {} and column {}", row, column);

		// Return the seat.
		return seats[ row ][ column ];
	}
}
