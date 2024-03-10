package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
				assignPrice(seats[ i ][ j ]);
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

	private void assignPrice(Seat seat) {
		LOGGER.debug("Assigning price to seat at row {} column {}", seat.getRow(), seat.getColumn());
		if (seat.getRow() <= 3) {
			seat.setPrice(10);
		} else {
			seat.setPrice(8);
		}
		LOGGER.debug("Assigning price to {}", seat.getPrice());
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
