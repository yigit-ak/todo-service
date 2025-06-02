package net.yigitak.todoapp.models;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecurrentSubtaskTemplate {

  @NotEmpty( message = "Task title cannot be empty." )
  String title;

  String description;

}
