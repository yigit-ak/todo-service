package net.yigitak.todoapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public record CreateTaskDto(
    @NotBlank(message = "Task title cannot be blank.") String title,
    @Valid List<CreateSubtaskDto> subtasks,
    @FutureOrPresent(message = "Task cannot be assigned on a date in the past.")
        LocalDate dateAssigned,
    @FutureOrPresent(message = "Due date cannot be in the past.") LocalDate dateDue,
    String description) {}
