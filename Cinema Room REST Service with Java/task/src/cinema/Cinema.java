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

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
}
