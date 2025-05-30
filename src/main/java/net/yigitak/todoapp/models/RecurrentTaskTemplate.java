package net.yigitak.todoapp.models;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecurrentTaskTemplate {

  @NotEmpty( message = "Task title cannot be empty." )
  String title;

  @Valid
  List< RecurrentSubtaskTemplate > subtasks;

  String description;

}
