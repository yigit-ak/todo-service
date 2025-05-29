package net.yigitak.todoapp.services;


import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.dto.CreateSubtaskDto;
import net.yigitak.todoapp.dto.CreateTaskDto;
import net.yigitak.todoapp.exceptions.EntityNotFoundException;
import net.yigitak.todoapp.mappers.TaskMapper;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.models.Subtask;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;


  public List < Task > getAllTasksByOwnerId ( String ownerId ) {

    return taskRepository.findAllByOwnerId( ownerId );
  }


  public Task createTask ( CreateTaskDto dto , String ownerId ) {

    Task task = taskMapper.toEntity( dto , ownerId );
    return taskRepository.save( task );
  }


  public Task getTaskByIdAndOwnerId ( String id , String ownerId ) {

    return taskRepository
        .findByIdAndOwnerId( id , ownerId )
        .orElseThrow( ( ) -> new EntityNotFoundException( "Task not found." ) );
  }


  public void deleteTaskByIdAndOwnerId ( String taskId , String ownerId ) {

    Task taskToDelete = getTaskByIdAndOwnerId( taskId , ownerId );
    taskRepository.delete( taskToDelete );
  }


  public Task addSubtaskToTaskByOwnerId ( String taskId , CreateSubtaskDto dto , String ownerId ) {

    Task parentTask = getTaskByIdAndOwnerId( taskId , ownerId );
    Subtask subtask = taskMapper.toEntity( dto );
    parentTask.getSubtasks().add( subtask );
    return taskRepository.save( parentTask );
  }

  //  public Task updateTaskByIdAndOwnerId(String taskId, Map<String, Object> updateRequest, String
  // ownerId) {
  //    Task taskToUpdate = taskRepository.findByIdAndOwner(taskId, owner);
  //
  //    if (taskToUpdate == null)
  //      return null; // TODO: don't return null, return 404 or something or auth error
  //
  //    if (updateRequest.containsKey("title"))
  //      taskToUpdate.setTitle((String) updateRequest.get("title"));
  //    if (updateRequest.containsKey("description"))
  //      taskToUpdate.setDescription((String) updateRequest.get("description"));
  //    if (updateRequest.containsKey("tagId"))
  //      taskToUpdate.setTag(new Tag((String) updateRequest.get("tagId")));
  //    if (updateRequest.containsKey("starred"))
  //      taskToUpdate.setStarred((Boolean) updateRequest.get("starred"));
  //    if (updateRequest.containsKey("dateAssigned"))
  //      taskToUpdate.setDateAssigned(LocalDate.parse((String) updateRequest.get("dateAssigned")));
  //    if (updateRequest.containsKey("dateDue"))
  //      taskToUpdate.setDateDue(LocalDate.parse((String) updateRequest.get("dateDue")));
  //    // if (updateRequest.containsKey("subtasks")) TODO: idk what to do
  //
  //    return taskRepository.save(taskToUpdate);
  //  }


  public void deleteSubtaskByParentTaskIdAndOwnerId (
      String taskId , String subtaskId , String ownerId ) {

    Task parentTask = getTaskByIdAndOwnerId( taskId , ownerId );
    parentTask.getSubtasks().removeIf( subtask -> subtask.getId().equals( subtaskId ) );
    taskRepository.save( parentTask );
  }


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


  public Task toggleCompleted ( String userId , String taskId ) {

    Task task = getTaskByIdAndOwnerId( taskId , userId );
    task.setCompleted( !task.getCompleted() );
    return taskRepository.save( task );
  }


  public Subtask toggleSubtaskCompleted ( String userId , String taskId , String subtaskId ) {

    Task task = getTaskByIdAndOwnerId( taskId , userId );

    Subtask subtask = task.getSubtasks().stream()
        .filter( s -> s.getId().equals( subtaskId ) )
        .findFirst()
        .orElseThrow( ( ) -> new EntityNotFoundException( "Subtask not found" ) );

    subtask.setCompleted( !subtask.isCompleted() );
    taskRepository.save( task );

    return subtask;
  }
}
