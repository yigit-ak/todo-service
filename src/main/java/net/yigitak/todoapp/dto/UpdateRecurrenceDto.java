package net.yigitak.todoapp.dto;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import net.yigitak.todoapp.models.RecurrentTaskTemplate;

import java.time.LocalDate;
import java.util.Optional;


public record UpdateRecurrenceDto(
    Optional< RecurrentTaskTemplate > taskTemplate ,

    @FutureOrPresent( message = "Start date cannot be in the past." ) Optional< LocalDate > startDate ,

    @FutureOrPresent( message = "End date cannot be in the past." ) Optional< LocalDate > endDate ,

    @Positive( message = "Period must be a positive integer." ) Optional< Integer > period
) { }
