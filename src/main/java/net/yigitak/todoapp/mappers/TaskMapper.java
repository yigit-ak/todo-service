package net.yigitak.todoapp.mappers;


import net.yigitak.todoapp.dto.CreateSubtaskDto;
import net.yigitak.todoapp.dto.CreateTaskDto;
import net.yigitak.todoapp.models.Subtask;
import net.yigitak.todoapp.models.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class TaskMapper {

    public Task toEntity(CreateTaskDto dto, String ownerId) {

    Task task = new Task();
    task.setOwnerId(ownerId);
    task.setTitle(dto.title());
    // Unwrap optional description, defaulting to null if absent
    task.setDescription(dto.description().orElse(null));
    // Unwrap optional dateAssigned, defaulting to null if absent
    task.setDateAssigned(dto.dateAssigned().orElse(null));
    // dateDue is mandatory (non-optional)
    task.setDateDue(dto.dateDue().orElse(null));
    // Map subtasks if present, otherwise use an empty list
    Set<Subtask> subtasks = dto.subtasks()
            .orElseGet(List::of)
            .stream()
            .map(this::toEntity)
            .collect(Collectors.toSet());
    task.setSubtasks(subtasks);
    return task;
  }


    public Subtask toEntity(CreateSubtaskDto dto) {

    Subtask subtask = new Subtask();
    subtask.setTitle(dto.title());
    // Unwrap optional description, defaulting to null if absent
    subtask.setDescription(dto.description().orElse(null));
    return subtask;
  }

}
