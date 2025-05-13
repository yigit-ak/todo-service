package net.yigitak.todoapp.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSubtaskDto(
    @NotBlank(message = "Task title cannot be blank.") String title, String description) {}
