package net.yigitak.todoapp.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode( exclude = "tasks" )

@Document( "recurrences" )
public class Recurrence {

  @Id
  private String id;

  @DocumentReference( lazy = true )
  private Set< Task > tasks;

  @Indexed
  private String ownerId;

  @CreatedDate
  private LocalDateTime timeCreated;

  @LastModifiedDate
  private LocalDateTime timeModified;

  private RecurrentTaskTemplate taskTemplate;

  @Indexed
  private LocalDate startDate;

  @Indexed
  private LocalDate endDate;
  private LocalDate lastOccurrence;

  /**
   * e.g. “every 7 days” → period = 7
   */
  private int period;


  public boolean isForever ( ) {
    return getEndDate() == null;
  }

}
