package net.yigitak.todoapp.models;


import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@NoArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true, of = { "id" } )

public class Subtask {

  @EqualsAndHashCode.Include
  private String id = new ObjectId().toHexString();

  private String  title;
  private String  description;
  private boolean completed = false;
  private LocalDateTime timeCreated = LocalDateTime.now();

}
