package net.yigitak.todoapp.services;


import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.dto.CreateRecurrenceDto;
import net.yigitak.todoapp.dto.UpdateRecurrenceDto;
import net.yigitak.todoapp.exceptions.EntityNotFoundException;
import net.yigitak.todoapp.mappers.RecurrenceMapper;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.repositories.RecurrenceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RecurrenceService {

  private final RecurrenceRepository recurrenceRepository;
  private final RecurrenceMapper recurrenceMapper;
  private final RecurrentTaskService recurrentTaskService;


  public List< Recurrence > getAllRecurrencesByOwnerId ( String ownerId ) {
    return recurrenceRepository.findAllByOwnerId( ownerId );
  }


  public Recurrence createRecurrence ( CreateRecurrenceDto dto , String ownerId ) {
    Recurrence recurrence = recurrenceMapper.toEntity( dto , ownerId );
    return recurrenceRepository.save( recurrence );
  }


  public Recurrence getRecurrenceByIdAndOwnerId ( String recurrenceId , String ownerId ) {
    return recurrenceRepository
        .findByIdAndOwnerId( recurrenceId , ownerId )
        .orElseThrow( ( ) -> new EntityNotFoundException(
            "Recurrence with the ID %s not found.".formatted( recurrenceId ) ) );
  }


  public void deleteRecurrenceByIdAndOwnerId ( String recurrenceId , String ownerId ) {
    recurrenceRepository.deleteByIdAndOwnerId( recurrenceId , ownerId );
  }


  public Recurrence updateRecurrenceByIdAndOwner (
      String recurrenceId , String ownerId , UpdateRecurrenceDto dto ) {

    Recurrence recurrenceToUpdate = getRecurrenceByIdAndOwnerId( recurrenceId , ownerId );

    dto.startDate().ifPresent( startDate -> {
      recurrentTaskService.deleteAllRecurrentTasksStartingFromToday( recurrenceToUpdate , ownerId );

      /* when asked for the tasks on a given date, all recurrent tasks will be created according
      to updated startDate by DateService. */

      recurrenceToUpdate.setStartDate( startDate );
      recurrenceToUpdate.setLastOccurrence( null );
    } );


    dto.endDate().ifPresent( newEndDate -> {
      LocalDate oldEndDate = recurrenceToUpdate.getEndDate();

      if ( recurrenceToUpdate.isForever() || newEndDate.isBefore( oldEndDate ) ) {
        recurrentTaskService.deleteAllRecurrentTasksStartingFrom(
            recurrenceToUpdate , newEndDate , ownerId );
        Task lastCreatedTask =
            recurrentTaskService.getLastCreatedRecurrentTask( recurrenceToUpdate , ownerId );
        recurrenceToUpdate.setLastOccurrence( lastCreatedTask.getDateAssigned() );
      }

      recurrenceToUpdate.setEndDate( newEndDate );
    } );

    dto.period().ifPresent( period -> {
      recurrentTaskService.deleteAllRecurrentTasksStartingFromToday( recurrenceToUpdate , ownerId );

      /* when asked for the tasks on a given date, all recurrent tasks will be created according
      to updated startDate by DateService. */

      Task lastCreatedTask =
          recurrentTaskService.getLastCreatedRecurrentTask( recurrenceToUpdate , ownerId );
      recurrenceToUpdate.setLastOccurrence( lastCreatedTask.getDateAssigned() );
      recurrenceToUpdate.setPeriod( period );
    } );


    dto.taskTemplate().ifPresent( taskTemplate -> {
      recurrentTaskService.deleteAllRecurrentTasksStartingFromToday( recurrenceToUpdate , ownerId );
      recurrenceToUpdate.setTaskTemplate( taskTemplate );
    } );

    return recurrenceRepository.save( recurrenceToUpdate );
  }

}
