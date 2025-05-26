package net.yigitak.todoapp.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateSubtaskDto(

        @NotBlank(message = "Task title cannot be blank.")
    String title,

    Optional<String> description

) {}
