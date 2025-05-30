package net.yigitak.todoapp.models;


import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecurrentSubtaskTemplate {

  @NotEmpty( message = "Task title cannot be empty." )
  String title;

  String description;

}
