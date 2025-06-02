package net.yigitak.todoapp.models;


import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( of = { "id" } )
public class Subtask {

  private String id = new ObjectId().toHexString();

  private String  title;
  private String  description;
  private boolean completed = false;
  private LocalDateTime timeCreated = LocalDateTime.now();

}
