package net.yigitak.todoapp.exceptions;

import java.io.InvalidObjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(InvalidObjectException.class)
  public ResponseEntity<?> handleException(InvalidRequestException e) {
    return ResponseEntity.badRequest().build(); // todo add the messages
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleException(EntityNotFoundException e) {
    return ResponseEntity.notFound().build();
  }
}
