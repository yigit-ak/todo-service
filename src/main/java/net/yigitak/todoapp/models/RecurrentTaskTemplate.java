package net.yigitak.todoapp.models;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecurrentTaskTemplate {

  @NotEmpty( message = "Task title cannot be empty." )
  String title;

  @Valid
  List< RecurrentSubtaskTemplate > subtasks;

  String description;

}
