package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
		String errorMessage = "Error: Media type not acceptable. Detail: " + ex.getMessage() + ". Cause: " + ex.getCause();
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		String errorMessage = "Error: No handler found. Detail: " + ex.getMessage() + ". Cause: " + ex.getCause();
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
		String errorMessage = "Error: " + ex.getMessage() + ". Cause: " + ex.getCause();
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

}
