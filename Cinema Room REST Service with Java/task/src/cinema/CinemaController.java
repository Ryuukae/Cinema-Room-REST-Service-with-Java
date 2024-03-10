package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class CinemaController {
	private final Cinema Cinema;

	public CinemaController(Cinema Cinema) {
		this.Cinema = Cinema;
	}

	@GetMapping("/seats")
	public ResponseEntity<Map<String, Object>> getSeats() {
		Map<String, Object> response = new HashMap<>();
		response.put("rows", Cinema.getRows());
		response.put("columns", Cinema.getColumns());

		List<Map<String, Integer>> seatsList = new ArrayList<>();
		Seat[][] seats = Cinema.getSeats();
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[ i ].length; j++) {
				Map<String, Integer> seatMap = new HashMap<>();
				seatMap.put("row", i + 1);
				seatMap.put("column", j + 1);
				seatsList.add(seatMap);
			}
		}
		response.put("seats", seatsList);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/purchase")
	public ResponseEntity<Map<String, Object>> purchase(@RequestParam int row, @RequestParam int column) {
		Map<String, Object> response = new HashMap<>();
		try {
			Seat seat = Cinema.getSeat(row, column);
			if (seat == null) {
				response.put("error", "Seat is not available");
			} else if (seat.isBooked()) {
				response.put("error", "Seat is already booked");
			} else {
				Cinema.getSeat(row, column).setBooked(true);
				response.put("success", "Seat booked");
			}
		} catch (Exception e) {
			response.put("error", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

}
