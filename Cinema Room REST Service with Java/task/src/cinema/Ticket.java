package cinema;

public class Ticket {
	private int row;
	private int column;
	private int price;

	public Ticket(int row, int column, int price) {
		this.row = row + 1;
		this.column = column + 1;
		this.price = price;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getPrice() {
		return price;
	}
}
