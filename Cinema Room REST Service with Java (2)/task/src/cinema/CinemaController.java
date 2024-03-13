package cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class CinemaController {

	// Declare our cinema object
	private final Cinema cinema;
	// Initializing a Logger object
	private static final Logger logger = LoggerFactory.getLogger(CinemaController.class);

	// We are injecting a Cinema object into our controller
	public CinemaController(Cinema cinema) {
		this.cinema = cinema;
		logger.debug("CinemaController created with Cinema object"); // Logger at the initialization of CinemaController
	}

	@GetMapping("/seats")
	public ResponseEntity<Map<String, Object>> getSeats() {
		logger.debug("Processing GET request to /seats"); // Logger at the start of getSeats processing
		Map<String, Object> response = new HashMap<>();
		// Adding cinema's rows and columns information to the response
		response.put("rows", cinema.getRows());
		response.put("columns", cinema.getColumns());

		// Generating a list of seats present in the cinema
		List<Map<String, Integer>> seatsList = generateSeatList(cinema);
		response.put("seats", seatsList);

		logger.debug("Sending response with seat info"); // Logger at the end of getSeats processing
		return ResponseEntity.ok(response); // Returning HTTP 200 OK with body (seat info)
	}

	// A helper method to generate a list of seats information
	private static List<Map<String, Integer>> generateSeatList(Cinema cinema) {
		List<Map<String, Integer>> seatsList = new ArrayList<>();
		Seat[][] seats = cinema.getSeats();
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[ i ].length; j++) {
				Map<String, Integer> seatMap = new HashMap<>();
				// Adapting the row and column value into human-friendly (starting from 1 not 0)
				seatMap.put("row", i + 1);
				seatMap.put("column", j + 1);
				seatMap.put("price", seats[ i ][ j ].getPrice());
				seatsList.add(seatMap);
			}
		}
		logger.debug("Generated seat list"); // Logger after seat list generation
		return seatsList;
	}

	@PostMapping("/purchase")
	public ResponseEntity<Map<String, Object>> purchase(@RequestBody Map<String, Integer> seatRequest) {
		logger.debug("Processing POST request to /purchase"); // Beginning processing of /purchase POST request
		Map<String, Object> response = new HashMap<>();
		int row = seatRequest.get("row") - 1;
		int column = seatRequest.get("column") - 1;
		try {
			Seat seat = cinema.getSeat(row, column);
			if (!seat.isBooked()) { // If the seat isn't booked
				cinema.bookSeat(seat); // Now we book the seat
				response.put("token", seat.getToken().toString());
				response.put("ticket", seat.getTicket());
				logger.debug("Ticket successfully booked, sending response."); // Seat booked successfully
				return ResponseEntity.ok(response);
			} else { // If seat is already booked, generate error
				logger.debug("Seat already booked, sending error."); // Seat already booked
				response.put("error", "The ticket has been already purchased!");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.debug("Invalid row or column, sending error."); // Seat coordinates out of bounds
			response.put("error", "The number of a row or a column is out of bounds!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/return")
	public ResponseEntity<Map<String, Object>> handleReturn(@RequestBody Map<String, Object> request) {
		logger.debug("Processing POST request to /return"); // Beginning processing of /return POST request
		Map<String, Object> response = new HashMap<>();
		String token = extractToken(request);

		Optional<Seat> optionalSeat = Optional.ofNullable(cinema.getSeatByToken(token));
		if (optionalSeat.isPresent()) {
			Seat seat = optionalSeat.get();
			seat.setBooked(false); // Un-book the seat
			logger.debug("Returning seat, sending response."); // Successfully returned the seat
			response.put("ticket", seat.getTicket());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else { // Invalid token error
			logger.debug("Provided token is invalid, sending error."); // Invalid token
			response.put("error", "Wrong token!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	// A helper method to clean the token string
	private String extractToken(Map<String, Object> request) {
		String token = (String) request.get("token");
		if (token.startsWith("\"token\":")) {
			token = token.substring(8, token.length() - 1); // Cleaning up the string
		}
		logger.debug("Token extracted"); // Token extracted successfully
		return token;
	}
}
