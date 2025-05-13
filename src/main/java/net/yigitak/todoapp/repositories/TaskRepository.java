package net.yigitak.todoapp.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TaskRepository extends MongoRepository<Task, String> {

  Optional<Task> findByIdAndOwnerId(String taskId, String ownerId);

  List<Task> findAllByOwnerId(String ownerId);

  List<Task> findAllByDateAssignedAndOwnerId(LocalDate dateAssigned, String ownerId);

  @Query(value = "{ 'recurrence': ?0, 'ownerId': ?1 }", sort = "{ 'dateAssigned': -1 }")
  Optional<Task> findLastCreatedRecurrentTask(Recurrence recurrence, String ownerId);

  @Query(value = "{ 'recurrence': ?0, 'dateAssigned': { $gte: ?1 }, 'ownerId': ?2 }", delete = true)
  void deleteAllByRecurrenceStartingFrom(Recurrence recurrence, LocalDate date, String ownerId);
}
