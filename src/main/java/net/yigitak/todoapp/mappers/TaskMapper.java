package net.yigitak.todoapp.mappers;

import java.util.stream.Collectors;
import net.yigitak.todoapp.dto.CreateSubtaskDto;
import net.yigitak.todoapp.dto.CreateTaskDto;
import net.yigitak.todoapp.models.Subtask;
import net.yigitak.todoapp.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

  public Task toEntity(CreateTaskDto dto, String ownerId) {
    Task task = new Task();

    task.setOwnerId(ownerId);
    task.setTitle(dto.title());
    task.setDescription(dto.description());
    task.setDateAssigned(dto.dateAssigned());
    task.setDateDue(dto.dateDue());
    task.setSubtasks(dto.subtasks().stream().map(this::toEntity).collect(Collectors.toSet()));

    return task;
  }

  public Subtask toEntity(CreateSubtaskDto dto) {
    Subtask subtask = new Subtask();
    subtask.setTitle(dto.title());
    subtask.setDescription(dto.description());
    return subtask;
  }
}
