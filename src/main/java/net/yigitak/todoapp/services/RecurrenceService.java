package net.yigitak.todoapp.services;


import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.dto.CreateRecurrenceDto;
import net.yigitak.todoapp.exceptions.EntityNotFoundException;
import net.yigitak.todoapp.mappers.RecurrenceMapper;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.repositories.RecurrenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecurrenceService {

  private final RecurrenceRepository recurrenceRepository;
  private final RecurrenceMapper recurrenceMapper;


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

  //  public Recurrence updateRecurrenceByIdAndOwner(
  //      String recurrenceId, String ownerId, Map<String, Object> dto) {
  //
  //    Recurrence recurrenceToUpdate = getRecurrenceByIdAndOwnerId(recurrenceId, ownerId);
  //
  //    if (dto.containsKey("startDate")) {
  //      LocalDate newStartDate = LocalDate.parse((String) dto.get("startDate"));
  //      taskService.deleteAllRecurrentTasksStartingFromToday(recurrenceToUpdate);
  //      // when asked for the tasks on a date, all recurrent tasks will be created according to
  //      // updated startDate.
  //      recurrenceToUpdate.setLastOccurrence(null);
  //      recurrenceToUpdate.setStartDate(newStartDate);
  //    }
  //
  //    if (dto.containsKey("endDate")) {
  //      LocalDate newEndDate = LocalDate.parse((String) dto.get("endDate"));
  //      LocalDate oldEndDate = recurrenceToUpdate.getEndDate();
  //      if (recurrenceToUpdate.isForever() || newEndDate.isBefore(oldEndDate)) {
  //        taskService.deleteAllRecurrentTasksStartingFrom(recurrenceToUpdate, newEndDate);
  //        Task lastCreatedTask = taskService.getLastCreatedRecurrentTask(recurrenceToUpdate);
  //        recurrenceToUpdate.setLastOccurrence(lastCreatedTask.getDateAssigned());
  //      }
  //      recurrenceToUpdate.setEndDate(newEndDate);
  //    }
  //
  //    if (dto.containsKey("period")) {
  //      int newPeriod = Integer.parseInt((String) dto.get("period"));
  //      taskService.deleteAllRecurrentTasksStartingFromToday(recurrenceToUpdate);
  //      /* when asked for the tasks on a date, all recurrent tasks will be created according to
  //      updated period. */
  //      Task lastCreatedTask = taskService.getLastCreatedRecurrentTask(recurrenceToUpdate);
  //      recurrenceToUpdate.setLastOccurrence(lastCreatedTask.getDateAssigned());
  //      recurrenceToUpdate.setPeriod(newPeriod);
  //    }
  //
  //    if (dto.containsKey("tagId")) {
  //      String newTagId = (String) dto.get("tagId");
  //      recurrenceToUpdate.setTag(new Tag(newTagId));
  //    }
  //
  //    // TODO: if (updates.containsKey("taskTemplate"))
  //
  //    return recurrenceRepository.save(recurrenceToUpdate);
  //  }
}
