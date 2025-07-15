package net.yigitak.todoapp.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateTaskDto(

        @NotBlank(message = "Task title cannot be blank.") String title,

        @Valid Optional<List<CreateSubtaskDto>> subtasks,

        @FutureOrPresent(message = "Task cannot be assigned on a date in the past.") Optional<LocalDate> dateAssigned,

        @FutureOrPresent(message = "Due date cannot be in the past.") Optional<LocalDate> dateDue,

        Optional<String> description

) {
    public CreateTaskDto {
        subtasks = subtasks == null ? Optional.empty() : subtasks;
        dateAssigned = dateAssigned == null ? Optional.empty() : dateAssigned;
        dateDue = dateDue == null ? Optional.empty() : dateDue;
        description = description == null ? Optional.empty() : description;
    }
}
