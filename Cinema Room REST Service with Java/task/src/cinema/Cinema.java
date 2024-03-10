package cinema;

import org.springframework.stereotype.Service;

@Service
public class Cinema {
	private final int rows = 9;
	private final int columns = 9;
	private final Seat[][] seats;

	public Cinema() {
		seats = new Seat[ rows ][ columns ];
		initializeSeats();
	}

	private void initializeSeats() {
		int idCounter = 1;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				seats[ i ][ j ] = new Seat();
			}
		}
	}


	public Seat[][] getSeats() {
		return seats;
	}

	private void bookSeat(Seat seat) {
		if (seat.isBooked()) {
			throw new IllegalStateException("Seat is already booked");
		} else {
			seat.setBooked(true);
		}
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
