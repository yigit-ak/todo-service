package net.yigitak.todoapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("recurrences")
@Setter
@Getter
@NoArgsConstructor
public class Recurrence {

  @Id private String id;

  @DocumentReference private Set<Task> tasks;

  private String ownerId;
  private RecurrentTaskTemplate taskTemplate;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate lastOccurrence;
  private int period;
  private LocalDateTime timeCreated = LocalDateTime.now();

  public boolean isForever() {
    return getEndDate() == null;
  }
}
