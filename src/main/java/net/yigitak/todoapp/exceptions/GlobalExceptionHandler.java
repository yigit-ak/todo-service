package net.yigitak.todoapp.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.InvalidObjectException;


@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler( InvalidObjectException.class )
  public ResponseEntity< ? > handleException ( InvalidRequestException e ) {

    return ResponseEntity.badRequest().build(); // todo add the messages
  }


  @ExceptionHandler( EntityNotFoundException.class )
  public ResponseEntity< ? > handleException ( EntityNotFoundException e ) {

    return ResponseEntity.notFound().build();
  }

}
