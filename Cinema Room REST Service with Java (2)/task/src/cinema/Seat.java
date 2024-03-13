package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

public class Seat {

    private UUID token; // identification token
    private boolean booked; // booking status
    private int price; // price of the seat
    private int row; // row number of the seat
    private int column; // column number of the seat
    private Ticket ticket; // ticket related to this seat
    private static final Logger LOGGER = LoggerFactory.getLogger(Seat.class); // Logger instance

    public Seat(int row, int column) {
        LOGGER.debug("Creating new seat with row: {} and column: {}", row, column);
        this.token = UUID.randomUUID(); // generating random UUID for a seat
        // log the assigned token for debugging
        LOGGER.debug("Assigned Token: {}", this.token);
        this.booked = false; // initial booking status
        // log initial booking status for debugging

        LOGGER.debug("Initial booked status set to: {}", this.booked);
        this.row = row; // assignment of row

        // log assigned row for debugging
        LOGGER.debug("Set row to: {}", this.row);
        this.column = column; // assignment of column

        // log assigned column for debugging
        LOGGER.debug("Set column to: {}", this.column);
    }

    public UUID getToken() {
        // log the fetched token for debugging
        LOGGER.debug("Getting Token: {}", this.token);
        return this.token;
    }

    public void setToken(UUID token) {
        // log the new token for debugging
        LOGGER.debug("Setting Token: {}", token);
        this.token = token; // updating token
    }

    public boolean isBooked() {
        // log the result for debugging
        LOGGER.debug("Checking if the seat is booked: {}", this.booked);
        return this.booked; // returning booking status
    }

    public void setBooked(boolean booked) {
        // log the updated booking status for debugging
        LOGGER.debug("Changing the booking status of the seat to: {}", booked);
        this.booked = booked; // updating booking status
    }

    public int getPrice() {
        // log the fetched price for debugging
        LOGGER.debug("Getting seat price: {}", this.price);
        return this.price; // returning price
    }

    public void setPrice(int price) {
        // log the new price for debugging
        LOGGER.debug("Setting seat price as: {}", price);
        this.price = price; // updating price
    }

    public int getRow() {
        // log row number for debugging
        LOGGER.debug("Getting seat row: {}", this.row);
        return this.row; // returning row
    }

    public void setRow(int row) {
        // log new row number for debugging
        LOGGER.debug("Setting seat row as: {}", row);
        this.row = row; // updating row
    }

    public int getColumn() {
        // log column number for debugging
        LOGGER.debug("Getting seat column: {}", this.column);
        return this.column; // returning column
    }

    public void setColumn(int column) {
        // log new column number for debugging
        LOGGER.debug("Setting seat column as: {}", column);
        this.column = column; // updating column
    }

    public void assignPrice(Seat seat) {
        LOGGER.debug("Assigning price to seat at row {} column {}", seat.getRow(), seat.getColumn());
        if (seat.getRow() <= 3) {
            seat.setPrice(10);  // setting price
            LOGGER.debug("Seat price set to: {}", 10);
        } else {
            seat.setPrice(8);  // setting price
            LOGGER.debug("Seat price set to: {}", 8);
        }
        LOGGER.debug("Final calculated price: {}", seat.getPrice());
    }

    public void initializeTickets(Seat seat) {
        LOGGER.debug("Initializing tickets for seat at row {} column {}", seat.getRow(), seat.getColumn());
        this.ticket = new Ticket(seat.getRow(), seat.getColumn(), seat.getPrice()); // creating an instance of Ticket
        LOGGER.debug("Ticket created with row: {}, column: {}, price: {}", ticket.getRow(), ticket.getColumn(), ticket.getPrice());
    }

    public Ticket getTicket() {
        LOGGER.debug("Getting information about the Ticket for this seat");
        return this.ticket; // returning ticket information
    }
}
