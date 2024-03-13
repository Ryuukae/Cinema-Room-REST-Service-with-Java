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
	private final Cinema Cinema;
	private static final Logger logger = LoggerFactory.getLogger(CinemaController.class);

	// Constructor with cinema initialization
	public CinemaController(Cinema Cinema) {
		this.Cinema = Cinema;
		logger.debug("CinemaController initialized with Cinema: {}", Cinema);
	}

	// Get request for seats layout
	@GetMapping("/seats")
	public ResponseEntity<Map<String, Object>> getSeats() {
		logger.info("getSeats() method called.");
		Map<String, Object> response = new HashMap<>();
		response.put("rows", Cinema.getRows());
		response.put("columns", Cinema.getColumns());

		// Populating seat list with row, column and price details
		List<Map<String, Integer>> seatsList = new ArrayList<>();
		Seat[][] seats = Cinema.getSeats();
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[ i ].length; j++) {
				Map<String, Integer> seatMap = new HashMap<>();
				seatMap.put("row", i + 1);
				seatMap.put("column", j + 1);
				seatMap.put("price", seats[ i ][ j ].getPrice());
				seatsList.add(seatMap);
			}
		}

		// Adding seats list to the response
		response.put("seats", seatsList);
		logger.debug("Response ready: {}", response);
		return ResponseEntity.ok(response);
	}

	// Post request for purchasing tickets
	@PostMapping("/purchase")
	public ResponseEntity<Map<String, Object>> purchase(@RequestBody Map<String, Integer> seatRequest) {
		logger.info("purchase() method called with request: {}", seatRequest);
		Map<String, Object> response = new HashMap<>();
		int row = seatRequest.get("row") - 1;
		int column = seatRequest.get("column") - 1;

		// Try to book the seat and handle exception if occurs
		try {
			Seat seat = Cinema.getSeat(row, column);

			// Check if seat is already booked
			if (seat.isBooked()) {
				response.put("error", "The ticket has been already purchased!");
				logger.warn("Seat already booked");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				// Seat is not booked, hence successfully book the seat
				Cinema.bookSeat(seat);
				response.put("token", seat.getToken().toString());
				response.put("ticket", seat.getTicket());
				logger.debug("Ticket purchased successfully");
				return ResponseEntity.ok(response);
			}
		} catch (Exception e) {
			// Handle out of bound exception
			logger.error("Exception occurred", e);
			response.put("error", "The number of a row or a column is out of bounds!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	// Post request to return tickets
	@PostMapping("/return")
	public ResponseEntity<Map<String, Object>> handleReturn(@RequestBody Map<String, Object> request) {
		logger.info("handleReturn() method called with request: {}", request);
		Map<String, Object> response = new HashMap<>();
		String token = extractToken(request);

		// Check if token exists and handle return if it does
		Optional<Seat> optionalSeat = Optional.ofNullable(Cinema.getSeatByToken(token));
		if (optionalSeat.isPresent()) {
			Seat seat = optionalSeat.get();
			seat.setBooked(false);
			Cinema.getSeatByToken(token).setBooked(false);
			Cinema.setIncome(Cinema.getIncome() - seat.getPrice());
			response.put("ticket", seat.getTicket());
			logger.debug("Ticket returned successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			// Handle error if token does not exists
			logger.warn("Wrong token provided");
			response.put("error", "Wrong token!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	// Get request for cinema stats, checking with password
	@GetMapping("/stats")
	private ResponseEntity<Map<String, Object>> returnStats(@RequestParam String password) {
		// check if password matches
		if (password.equals("super_secret")) {
			logger.info("returnStats() method called");

			Map<String, Object> response = new HashMap<>();
			response.put("income", Cinema.getIncome());
			response.put("available", Cinema.getAvailableSeats());
			response.put("purchased", Cinema.getTotalPurchasedTickets());

			logger.debug("Response ready: {}", response);
			return ResponseEntity.ok(response);
		} else {
			// Handle wrong password error
			Map<String, Object> response = new HashMap<>();
			response.put("error", "The password is wrong!");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

	}

	// Method to extract token from request
	private String extractToken(Map<String, Object> request) {
		logger.debug("extractToken() method called with request: {}", request);
		String token = (String) request.get("token");
		logger.debug("Token before processing: {}", token);
		if (token.startsWith("\"token\":")) {
			token = token.substring(8, token.length() - 1);
		}
		logger.debug("Token after processing: {}", token);
		return token;
	}
}
