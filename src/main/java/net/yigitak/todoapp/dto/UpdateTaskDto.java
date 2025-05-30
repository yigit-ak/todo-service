package net.yigitak.todoapp.dto;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;


public record UpdateTaskDto(

    Optional< @NotEmpty( message = "Title cannot be empty" ) String > title ,

    Optional< String > description ,

    Optional< @NotNull( message = "Completed flag must be true or false" ) Boolean > completed ,

    Optional< @NotNull( message = "Starred flag must be true or false" ) Boolean > starred ,

    Optional< @FutureOrPresent( message = "dateAssigned cannot be in the past" ) LocalDate > dateAssigned ,

    Optional< @FutureOrPresent( message = "dateDue cannot be in the past" ) LocalDate > dateDue

) { }
