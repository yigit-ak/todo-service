package net.yigitak.todoapp.services;


import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.exceptions.EntityNotFoundException;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class RecurrentTaskService {

  private final TaskRepository taskRepository;


  public void deleteAllRecurrentTasksStartingFromToday ( Recurrence recurrence , String ownerId ) {

    LocalDate today = LocalDate.now();
    taskRepository.deleteAllByRecurrenceStartingFrom( recurrence , today , ownerId );
  }


  public void deleteAllRecurrentTasksStartingFrom (
      Recurrence recurrence , LocalDate date , String ownerId ) {

    taskRepository.deleteAllByRecurrenceStartingFrom( recurrence , date , ownerId );
  }


  public Task getLastCreatedRecurrentTask ( Recurrence recurrence , String ownerId ) {

    return taskRepository
        .findLastCreatedRecurrentTask( recurrence , ownerId )
        .orElseThrow( ( ) -> new EntityNotFoundException( "No recurrent task found." ) );
  }

}
