package net.yigitak.todoapp.models;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Setter @Getter @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subtask {

  @EqualsAndHashCode.Include
  private String id = new ObjectId().toHexString();

  private String title;
  private String description;
  private boolean completed = false;
  private LocalDateTime timeCreated = LocalDateTime.now();

}
