package cinema;

import org.springframework.http.HttpStatus;
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
				seatMap.put("price", seats[ i ][ j ].getPrice());
				seatsList.add(seatMap);
			}
		}
		response.put("seats", seatsList);

		return ResponseEntity.ok(response);
	}


	@PostMapping("/purchase")
	public ResponseEntity<Map<String, Object>> purchase(@RequestBody Map<String, Integer> seatRequest) {
		Map<String, Object> response = new HashMap<>();
		int row = seatRequest.get("row") - 1;
		int column = seatRequest.get("column") - 1;
		try {
			Seat seat = Cinema.getSeat(row, column);
			if (seat.isBooked()) {
				response.put("error", "The ticket has been already purchased!");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // return status code 400
			} else {
				seat.setBooked(true);
				response.put("row", seat.getRow() + 1);
				response.put("column", seat.getColumn() + 1);
				response.put("price", seat.getPrice());
				return ResponseEntity.ok(response); // return status code 200
			}
		} catch (Exception e) {
			response.put("error", "The number of a row or a column is out of bounds!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // return status code 400
		}
	}

}
