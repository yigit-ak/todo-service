package net.yigitak.todoapp.services;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.repositories.RecurrenceRepository;
import net.yigitak.todoapp.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DateService {

  private final TaskRepository taskRepository;
  private final RecurrenceRepository recurrenceRepository;

  public List<Task> getAllTasksByAssignedDate(String ownerId, LocalDate date) {
    createNonExistentRecurrentTasksUntil(date, ownerId);
    return taskRepository.findAllByDateAssignedAndOwnerId(date, ownerId);
  }

  private void createNonExistentRecurrentTasksUntil(LocalDate givenDate, String ownerId) {

    boolean isGivenDateWithinOneYear = givenDate.isBefore(LocalDate.now().plusYears(1));

    if (isGivenDateWithinOneYear) {
      List<Recurrence> recurrences =
          recurrenceRepository.findAllByOwnerIdBetweenLastOccurrenceAndEndDateFor(
              ownerId, givenDate);
      for (Recurrence recurrence : recurrences) createRecurrentTasksUntil(recurrence, givenDate);
    }
  }

  private void createRecurrentTasksUntil(Recurrence recurrence, LocalDate date) {

    LocalDate lastOccurrence = recurrence.getLastOccurrence();
    int period = recurrence.getPeriod();

    List<Task> tasksToSave = new LinkedList<>();

    LocalDate startFrom =
        lastOccurrence == null ? recurrence.getStartDate() : lastOccurrence.plusDays(period);

    System.out.println("start from: " + startFrom);

    for (LocalDate taskDate = startFrom;
        taskDate.isBefore(date) || taskDate.isEqual(date);
        taskDate = taskDate.plusDays(period)) {
      Task task = new Task();
      task.setRecurrence(recurrence);
      task.setOwnerId(recurrence.getOwnerId());
      task.setDateAssigned(taskDate);
      tasksToSave.add(task);
      lastOccurrence = taskDate;
      System.out.println(task.getDateAssigned());
    }

    recurrence.setLastOccurrence(lastOccurrence);
    recurrenceRepository.save(recurrence);
    taskRepository.saveAll(tasksToSave);
  }
}
