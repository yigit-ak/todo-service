package net.yigitak.todoapp.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.yigitak.todoapp.annotations.JwtSubject;
import net.yigitak.todoapp.dto.CreateSubtaskDto;
import net.yigitak.todoapp.dto.CreateTaskDto;
import net.yigitak.todoapp.dto.UpdateSubtaskDto;
import net.yigitak.todoapp.dto.UpdateTaskDto;
import net.yigitak.todoapp.models.Subtask;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.services.DateService;
import net.yigitak.todoapp.services.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping( "/api/v1/tasks" )
@RequiredArgsConstructor
@Slf4j
public class TaskController {

  private final TaskService taskService;

  private final DateService dateService;


  @GetMapping
  public ResponseEntity< List< Task > > getAllTasks ( @JwtSubject String userId ) {

    List< Task > tasks = taskService.getAllTasksByOwnerId( userId );
    return ResponseEntity.ok( tasks );
  }


  @PostMapping
  public ResponseEntity< Task > createTask (
      @JwtSubject String userId ,
      @RequestBody CreateTaskDto dto
  ) {

    Task newTask = taskService.createTask( dto , userId );
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path( "/{id}" )
        .buildAndExpand( newTask.getId() )
        .toUri();
      return ResponseEntity.created(location).body(newTask);
  }


  @GetMapping( "/{task-id}" )
  public ResponseEntity< Task > getTaskById (
      @JwtSubject String userId ,
      @PathVariable( "task-id" ) String taskId
  ) {

    Task task = taskService.getTaskByIdAndOwnerId( taskId , userId );
    return ResponseEntity.ok( task );
  }


  @DeleteMapping( "/{task-id}" )
  public ResponseEntity< Task > deleteTaskById (
      @JwtSubject String userId ,
      @PathVariable( "task-id" ) String taskId
  ) {

    taskService.deleteTaskByIdAndOwnerId( taskId , userId );
    return ResponseEntity.noContent().build();
  }


  @PatchMapping( "/{task-id}" )
  public ResponseEntity< Void > updateTaskById (
      @JwtSubject String userId ,
      @PathVariable( "task-id" ) String taskId ,
      UpdateTaskDto dto
  ) {

    taskService.updateTaskByIdAndOwnerId( taskId , dto , userId );
    return ResponseEntity.ok().build();
  }


  @PostMapping( "/{task-id}/subtasks" )
  public ResponseEntity< Task > addSubtaskToTask (
      @JwtSubject String userId , @PathVariable( "task-id" ) String parentTaskId ,
      @RequestBody CreateSubtaskDto dto
  ) {

    taskService.addSubtaskToTaskByOwnerId( parentTaskId , dto , userId );
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .replacePath( "/tasks/{task-id}" )
        .buildAndExpand( parentTaskId )
        .toUri();
    return ResponseEntity.created( location ).build();
  }


  @PatchMapping( "/{task-id}/subtasks/{subtask-id}" )
  public ResponseEntity< Void > updateSubtaskByIdAndOwnerId (
      @JwtSubject String userId , @PathVariable( "task-id" ) String taskId ,
      @PathVariable( "subtask-id" ) String subtaskId , UpdateSubtaskDto dto
  ) {

    taskService.updateSubtaskByIdAndOwnerId( taskId , subtaskId , dto , userId );
    return ResponseEntity.ok().build();
  }


  @DeleteMapping( "/{task-id}/subtasks/{subtask-id}" )
  public ResponseEntity< Task > deleteSubtask (
      @JwtSubject String userId , @PathVariable( "task-id" ) String parentTaskId ,
      @PathVariable( "subtask-id" ) String subtaskId
  ) {

    taskService.deleteSubtaskByParentTaskIdAndOwnerId( parentTaskId , subtaskId , userId );
    return ResponseEntity.noContent().build();
  }


    @GetMapping("/")
  public ResponseEntity< List< Task > > getAllTasksAssignedOn (
      @JwtSubject String userId ,
      @RequestParam @DateTimeFormat( iso = DateTimeFormat.ISO.DATE ) LocalDate date
  ) {

    List< Task > tasks = dateService.getAllTasksByAssignedDate( userId , date );
    return ResponseEntity.ok( tasks );
  }


  @GetMapping( "/today" )
  public ResponseEntity< List< Task > > getAllTasksAssignedToday ( @JwtSubject String userId ) {
    log.info( "Getting all tasks assigned today for user: {}" , userId );
    LocalDate today    = LocalDate.now();
    List< Task > tasks = dateService.getAllTasksByAssignedDate( userId , today );
    return ResponseEntity.ok( tasks );
  }


  @PostMapping( "/{task-id}/toggle-completed" )
  public ResponseEntity< Task > toggleTaskCompleted (
      @JwtSubject String userId ,
      @PathVariable( "task-id" ) String taskId
  ) {

    Task task = taskService.toggleCompleted( userId , taskId );
    return ResponseEntity.ok( task );
  }


  @PostMapping( "/{task-id}/subtasks/{subtask-id}/toggle-completed" )
  public ResponseEntity< Subtask > toggleSubtaskCompleted (
      @JwtSubject String userId , @PathVariable( "task-id" ) String taskId ,
      @PathVariable( "subtask-id" ) String subtaskId
  ) {

    Subtask subtask = taskService.toggleSubtaskCompleted( userId , taskId , subtaskId );
    return ResponseEntity.ok( subtask );
  }

}
