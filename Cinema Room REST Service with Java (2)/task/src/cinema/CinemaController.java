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

	public CinemaController(Cinema Cinema) {
		this.Cinema = Cinema;
		logger.debug("CinemaController initialized with Cinema: {}", Cinema);
	}

	@GetMapping("/seats")
	public ResponseEntity<Map<String, Object>> getSeats() {
		logger.info("getSeats() method called.");
		Map<String, Object> response = new HashMap<>();
		response.put("rows", Cinema.getRows());
		response.put("columns", Cinema.getColumns());

		List<Map<String, Integer>> seatsList = new ArrayList<>();
		Seat[][] seats = Cinema.getSeats();
		logger.debug("Preparing seats list");
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[ i ].length; j++) {
				Map<String, Integer> seatMap = new HashMap<>();
				seatMap.put("row", i + 1);
				seatMap.put("column", j + 1);
				seatMap.put("price", seats[ i ][ j ].getPrice());
				seatsList.add(seatMap);
			}
		}

		response.put("seats", seatsList);
		logger.debug("Response ready: {}", response);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/purchase")
	public ResponseEntity<Map<String, Object>> purchase(@RequestBody Map<String, Integer> seatRequest) {
		logger.info("purchase() method called with request: {}", seatRequest);
		Map<String, Object> response = new HashMap<>();
		int row = seatRequest.get("row") - 1;
		int column = seatRequest.get("column") - 1;

		try {
			Seat seat = Cinema.getSeat(row, column);
			logger.debug("Fetched seat for purchase");

			if (seat.isBooked()) {
				response.put("error", "The ticket has been already purchased!");
				logger.warn("Seat already booked");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				Cinema.bookSeat(seat);
				response.put("token", seat.getToken().toString());
				response.put("ticket", seat.getTicket());
				logger.debug("Ticket purchased successfully");
				return ResponseEntity.ok(response);
			}
		} catch (Exception e) {
			logger.error("Exception occurred", e);
			response.put("error", "The number of a row or a column is out of bounds!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/return")
	public ResponseEntity<Map<String, Object>> handleReturn(@RequestBody Map<String, Object> request) {
		logger.info("handleReturn() method called with request: {}", request);
		Map<String, Object> response = new HashMap<>();
		String token = extractToken(request);

		Optional<Seat> optionalSeat = Optional.ofNullable(Cinema.getSeatByToken(token));
		if (optionalSeat.isPresent()) {
			Seat seat = optionalSeat.get();
			logger.debug("Fetched seat for return");
			seat.setBooked(false);
			Cinema.setIncome(Cinema.getIncome() - seat.getPrice());
			response.put("ticket", seat.getTicket());
			logger.debug("Ticket returned successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			logger.warn("Wrong token provided");
			response.put("error", "Wrong token!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/stats")
	private ResponseEntity<Map<String, Object>> returnStats(@RequestParam String password) {
		if (password.equals("super_secret")) {
			logger.info("returnStats() method called");

			Map<String, Object> response = new HashMap<>();
			response.put("income", Cinema.getIncome());
			response.put("available", Cinema.getAvailableSeats());
			response.put("purchased", Cinema.getTotalPurchasedTickets());

			logger.debug("Response ready: {}", response);
			return ResponseEntity.ok(response);
		} else {
			Map<String, Object> response = new HashMap<>();
			response.put("error", "The password is wrong!");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

	}

	private String extractToken(Map<String, Object> request) {
		logger.debug("extractToken() method called with request: {}", request);
		String token = (String) request.get("token");
		logger.debug("Token before processing: {}", token);
		if (token.startsWith("\"token\":")) {
			token = token.substring(8, token.length() - 1);
			logger.debug("Token after processing: {}", token);
		}
		return token;
	}
}
