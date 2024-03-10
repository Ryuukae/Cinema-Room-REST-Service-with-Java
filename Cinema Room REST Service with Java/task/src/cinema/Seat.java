package cinema;

public class Seat {
	private int idCount = 1;
	private int id;
	private boolean booked;

	public Seat() {
		this.id = idCount++;
		this.booked = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}
}
