package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Cinema {

	private static final Logger LOGGER = LoggerFactory.getLogger(Cinema.class);

	private final Seat[][] seats;
	private final int rows = 9;
	private final int columns = 9;
	private int income;
	private int availableSeats;
	private int totalPurchasedTickets;

	public Cinema() {
		LOGGER.debug("Constructing cinema with {} rows and {} columns", rows, columns);
		seats = new Seat[ rows ][ columns ];
		initializeSeats();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				LOGGER.debug("Assigning price for seat at row {} column {}", i, j);
				Seat seat = seats[ i ][ j ];
				seat.assignPrice(seats[ i ][ j ]);
				LOGGER.debug("Initializing tickets for seat at row {} column {}", i, j);
				seat.initializeTickets(seats[ i ][ j ]);
			}
		}
		LOGGER.debug("Cinema successfully created with {} rows and {} columns", rows, columns);
	}


	public int getIncome() {
		LOGGER.debug("Fetching total income");
		return income;
	}

	public int getAvailableSeats() {
		LOGGER.debug("Fetching available seats");
		return availableSeats;
	}

	public int getTotalPurchasedTickets() {
		LOGGER.debug("Fetching total purchased tickets");
		return totalPurchasedTickets;
	}


	private void initializeSeats() {
		LOGGER.debug("Initializing seats");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				LOGGER.trace("Creating new seat at row {} column {}", i, j);
				seats[ i ][ j ] = new Seat(i, j);
			}
		}
		LOGGER.debug("Seats successfully initialized");
	}

	public Seat getSeatByToken(String token) {
		LOGGER.debug("Getting seat by token: {}", token);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Seat seat = seats[ i ][ j ];
				if (seat.getToken().equals(token)) {
					LOGGER.debug("Seat found at row {} column {}", i, j);
					return seat;
				}
			}
		}
		LOGGER.debug("Seat not found for token: {}", token);
		return null;
	}

	private void bookSeat(Seat seat) {
		LOGGER.debug("Attempting to book seat at row {} column {}", seat.getRow(), seat.getColumn());
		if (seat.isBooked()) {
			LOGGER.debug("Seat at row {} column {} is already booked", seat.getRow(), seat.getColumn());
			throw new IllegalStateException("Seat is already booked");
		} else {
			seat.setBooked(true);
			LOGGER.debug("Seat at row {} column {} booked successfully", seat.getRow(), seat.getColumn());
		}
	}

	public Seat[][] getSeats() {
		LOGGER.debug("Fetching all seats of cinema");
		return seats;
	}

	public int getRows() {
		LOGGER.debug("Fetching number of rows");
		return rows;
	}

	public int getColumns() {
		LOGGER.debug("Fetching number of columns");
		return columns;
	}

	public Seat getSeat(int row, int column) {
		LOGGER.debug("Fetching seat at row {} column {}", row, column);
		return seats[ row ][ column ];
	}
}
