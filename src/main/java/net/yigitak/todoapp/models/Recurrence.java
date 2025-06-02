package net.yigitak.todoapp.models;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( of = { "id" } )
@Document( "recurrences" )
public class Recurrence {

  @Id
  private String id;

  @Version
  private Long version;

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

  @Indexed
  private LocalDate lastOccurrence;

  /**
   * e.g. “every 7 days” → period = 7
   */
  private int period;


  public boolean isForever ( ) {
    return getEndDate() == null;
  }

}
