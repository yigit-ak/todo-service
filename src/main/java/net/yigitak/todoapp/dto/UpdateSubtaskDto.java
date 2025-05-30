package net.yigitak.todoapp.dto;


import jakarta.validation.constraints.NotEmpty;

import java.util.Optional;


public record UpdateSubtaskDto(
    Optional< @NotEmpty( message = "Title cannot be empty" ) String > title ,

    Optional< String > description
) { }
