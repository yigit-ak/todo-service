package net.yigitak.todoapp.models;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Setter
@Getter
@NoArgsConstructor
public class Subtask {

  private String id = (new ObjectId()).toHexString();
  private String title;
  private String description;
  private boolean completed = false;
  private LocalDateTime timeCreated = LocalDateTime.now();
}
