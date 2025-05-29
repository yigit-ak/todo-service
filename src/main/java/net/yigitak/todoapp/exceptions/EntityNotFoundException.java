package net.yigitak.todoapp.exceptions;


public class EntityNotFoundException
    extends RuntimeException {

  public EntityNotFoundException ( String message ) {

    super( message );
  }

}
