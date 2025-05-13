package net.yigitak.todoapp.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.dto.CreateSubtaskDto;
import net.yigitak.todoapp.dto.CreateTaskDto;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.services.DateService;
import net.yigitak.todoapp.services.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private final DateService dateService;

  @GetMapping
  public ResponseEntity<List<Task>> getAllTasks(@RequestHeader("X-User-Id") String userId) {
    List<Task> tasks = taskService.getAllTasksByOwnerId(userId);
    return ResponseEntity.ok(tasks);
  }

  @PostMapping
  public ResponseEntity<Task> createTask(
      @RequestHeader("X-User-Id") String userId, @RequestBody CreateTaskDto dto) {

    Task newTask = taskService.createTask(dto, userId);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newTask.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{task-id}")
  public ResponseEntity<Task> getTaskById(
      @RequestHeader("X-User-Id") String userId, @PathVariable("task-id") String taskId) {

    Task task = taskService.getTaskByIdAndOwnerId(taskId, userId);
    return ResponseEntity.ok(task);
  }

  @DeleteMapping("/{task-id}")
  public ResponseEntity<Task> deleteTaskById(
      @RequestHeader("X-User-Id") String userId, @PathVariable("task-id") String taskId) {

    taskService.deleteTaskByIdAndOwnerId(taskId, userId);
    return ResponseEntity.noContent().build();
  }

  //  @PatchMapping("/{task-id}")
  //  public ResponseEntity<Task> updateTaskById(
  //      @RequestHeader("X-User-Id") String userId,
  //      @PathVariable("task-id") String taskId,
  //      @RequestBody Map<String, Object> dto) {
  //
  //    Task task = taskService.updateTaskByIdAndOwnerId(taskId, dto, userId);
  //    return ResponseEntity.ok(task);
  //  }

  @PostMapping("/{task-id}/subtasks")
  public ResponseEntity<Task> addSubtaskToTask(
      @RequestHeader("X-User-Id") String userId,
      @PathVariable("task-id") String parentTaskId,
      @RequestBody CreateSubtaskDto dto) {

    taskService.addSubtaskToTaskByOwnerId(parentTaskId, dto, userId);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .replacePath("/tasks/{task-id}")
            .buildAndExpand(parentTaskId)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  //    @PatchMapping("/{task-id}/subtasks/{subtask-id}")
  //    public ResponseEntity<Task> updateSubtask(@RequestHeader("X-User-Id") String
  // userId,@PathVariable("task-id") String taskId,
  //                                              @PathVariable("subtask-id") String subtaskId,
  //                                              @RequestBody Subtask subtask
  //                                              ) {
  //        User user = userService.getUserIdByEmail(userEmail);
  //        return ResponseEntity.ok(taskService.updateSubtaskByParentTaskIdOwnerId(taskId, subtask,
  // user));
  //    } TODO: review the code

  @DeleteMapping("/{task-id}/subtasks/{subtask-id}")
  public ResponseEntity<Task> deleteSubtask(
      @RequestHeader("X-User-Id") String userId,
      @PathVariable("task-id") String parentTaskId,
      @PathVariable("subtask-id") String subtaskId) {

    taskService.deleteSubtaskByParentTaskIdAndOwnerId(parentTaskId, subtaskId, userId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/date")
  public ResponseEntity<List<Task>> getAllTasksAssignedOn(
      @RequestHeader("X-User-Id") String userId,
      @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

    List<Task> tasks = dateService.getAllTasksByAssignedDate(userId, date);
    return ResponseEntity.ok(tasks);
  }
}
