package net.yigitak.todoapp.mappers;


import lombok.RequiredArgsConstructor;
import net.yigitak.todoapp.dto.CreateRecurrenceDto;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.repositories.RecurrenceRepository;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RecurrenceMapper {

  private final RecurrenceRepository recurrenceRepository;


  public Recurrence toEntity ( CreateRecurrenceDto dto , String ownerId ) {

    Recurrence recurrence = new Recurrence();

    recurrence.setOwnerId( ownerId );
    recurrence.setTaskTemplate( dto.taskTemplate() );
    recurrence.setStartDate( dto.startDate() );
    recurrence.setEndDate( dto.endDate() );
    recurrence.setPeriod( dto.period() );
    recurrence.setLastOccurrence( null );

    return recurrence;
  }

}
