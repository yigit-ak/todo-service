package net.yigitak.todoapp.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.models.Subtask;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.repositories.RecurrenceRepository;
import net.yigitak.todoapp.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class DateService {

  private final TaskRepository taskRepository;
  private final RecurrenceRepository recurrenceRepository;


  public List< Task > getAllTasksByAssignedDate ( String ownerId , LocalDate date ) {
    log.info( "Getting all tasks assigned on the date: {} for the user: {}" , date , ownerId );

    createNonExistentRecurrentTasksUntil( date , ownerId );
    return taskRepository.findAllByDateAssignedAndOwnerId( date , ownerId );
  }


  private void createNonExistentRecurrentTasksUntil ( LocalDate givenDate , String ownerId ) {
    log.info(
        "Creating non-existent recurrent tasks until the date: {} for the user: {}" , givenDate ,
        ownerId );
    boolean isGivenDateWithinOneYear = givenDate.isBefore( LocalDate.now().plusYears( 1 ) );
    log.info( "isGivenDateWithinOneYear: {}" , isGivenDateWithinOneYear );
    if ( isGivenDateWithinOneYear ) {
      List< Recurrence > recurrences = recurrenceRepository.findAllValid( ownerId , givenDate );
      log.info( "recurrences: {}" , recurrences.toString() );
      for ( Recurrence recurrence : recurrences )
        createRecurrentTasksUntil( recurrence , givenDate );
    }
  }


  private void createRecurrentTasksUntil ( Recurrence recurrence , LocalDate date ) {

    LocalDate    lastOccurrence = recurrence.getLastOccurrence();
    int          period         = recurrence.getPeriod();
    List< Task > tasksToSave    = new LinkedList<>();

    log.info( "lastOccurrence: {}" , lastOccurrence );
    log.info( "period: {}" , period );
    log.info( "tasks: {}" , tasksToSave );

    LocalDate startFrom =
        lastOccurrence == null ? recurrence.getStartDate() : lastOccurrence.plusDays( period );

    log.info( "startFrom: {}" , startFrom );

    String title       = recurrence.getTaskTemplate().getTitle();
    String description = recurrence.getTaskTemplate().getDescription();
    Set< Subtask > subtasks = recurrence
        .getTaskTemplate()
        .getSubtasks()
        .stream()
        .map( subtaskTemplate -> Subtask
            .builder()
            .title( subtaskTemplate.getTitle() )
            .description( subtaskTemplate.getDescription() )
            .build() )
        .collect( Collectors.toSet() );

    for ( LocalDate taskDate = startFrom ; taskDate.isBefore( date ) || taskDate.isEqual( date ) ;
          taskDate = taskDate.plusDays( period ) ) {
      log.info( "taskDate: {}" , taskDate );

      Task task = Task
          .builder()
          .recurrence( recurrence )
          .dateAssigned( taskDate )
          .description( description )
          .title( title )
          .ownerId( recurrence.getOwnerId() )
          .subtasks( subtasks )
          .build();

      lastOccurrence = taskDate;
      tasksToSave.add( task );
      log.info( "task created: {}" , task );
    }

    if ( !tasksToSave.isEmpty() ) {
      recurrence.setLastOccurrence( lastOccurrence );
      recurrenceRepository.save( recurrence );
      taskRepository.saveAll( tasksToSave );
    }
  }

}
