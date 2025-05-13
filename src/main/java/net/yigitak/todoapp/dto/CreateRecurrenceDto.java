package net.yigitak.todoapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import net.yigitak.todoapp.models.RecurrentTaskTemplate;

public record CreateRecurrenceDto(
    @Valid RecurrentTaskTemplate taskTemplate,
    @FutureOrPresent(message = "Starting date cannot be in the past.") LocalDate startDate,
    @FutureOrPresent(message = "Ending date cannot be in the past.") LocalDate endDate,
    @Positive(message = "Task period must be a positive integer.") int period) {}
