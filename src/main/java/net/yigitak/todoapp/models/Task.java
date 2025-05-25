package net.yigitak.todoapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.mongodb.annotations.Immutable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Document("tasks")
public class Task {

  @Id
  private String id;

  @DocumentReference
  private Recurrence recurrence;

  @Indexed
  private String ownerId;

  private Set<Subtask> subtasks = new HashSet<>();
  private String title;
  private String description;
  private Boolean starred = false;
  private Boolean completed = false;

  @Indexed
  private LocalDate dateAssigned;

  @Indexed
  private LocalDate dateDue;

  @CreatedDate
  private LocalDateTime timeCreated;

  @LastModifiedDate
  private LocalDateTime timeModified;

}
