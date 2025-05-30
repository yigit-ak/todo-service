package net.yigitak.todoapp.models;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

  @NotBlank( message = "Task title cannot be blank." )
  String title;

  @Valid
  List< RecurrentSubtaskTemplate > subtasks;

  String description;

}
