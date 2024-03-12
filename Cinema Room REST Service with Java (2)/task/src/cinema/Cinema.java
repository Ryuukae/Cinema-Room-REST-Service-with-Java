package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Cinema {

	private static final Logger LOGGER = LoggerFactory.getLogger(Cinema.class);

	private final int rows = 9;
	private final int columns = 9;
	private final Seat[][] seats;

	public Cinema() {
		LOGGER.debug("Constructing cinema with {} rows and {} columns", rows, columns);
		seats = new Seat[ rows ][ columns ];
		initializeSeats();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Seat seat = seats[ i ][ j ];
				seat.assignPrice(seats[ i ][ j ]);
				seat.initializeTickets(seats[ i ][ j ]);
			}
		}
	}

	private void initializeSeats() {
		LOGGER.debug("Initializing seats");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				seats[ i ][ j ] = new Seat(i, j);
			}
		}
	}

	public Seat getSeatByToken(String token) {
		LOGGER.debug("Getting seat by token: {}", token);
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getColumns(); j++) {
				Seat seat = seats[ i ][ j ];
				if (seat.getToken().equals(UUID.fromString(token))) {
					LOGGER.debug("Seat found: {}", seat);
					return seat;
				}
			}
		}
		LOGGER.debug("Seat not found");
		return null;
	}


	private void bookSeat(Seat seat) {
		LOGGER.debug("Attempting to book seat at row {} column {}", seat.getRow(), seat.getColumn());
		if (seat.isBooked()) {
			LOGGER.debug("Seat already booked, throwing exception");
			throw new IllegalStateException("Seat is already booked");
		} else {
			seat.setBooked(true);
			LOGGER.debug("Seat booked successfully");
		}
	}

	public Seat[][] getSeats() {
		return seats;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Seat getSeat(int row, int column) {
		return seats[ row ][ column ];
	}
}
