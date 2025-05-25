package net.yigitak.todoapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@NoArgsConstructor
@Document("tasks")
public class Task {

  @Id
  private String id;

  @DocumentReference
  private Recurrence recurrence;

  private String ownerId;
  private Set<Subtask> subtasks = new HashSet<>();
  private String title;
  private String description;
  private Boolean starred = false;
  private Boolean completed = false;
  private LocalDate dateAssigned;
  private LocalDate dateDue;
  private LocalDateTime timeCreated = LocalDateTime.now();
}
