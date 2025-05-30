package net.yigitak.todoapp.models;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecurrentSubtaskTemplate {

  @NotBlank( message = "Task title cannot be blank." )
  String title;

  String description;

}
