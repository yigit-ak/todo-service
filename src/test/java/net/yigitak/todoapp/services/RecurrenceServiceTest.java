package net.yigitak.todoapp.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import net.yigitak.todoapp.dto.CreateRecurrenceDto;
import net.yigitak.todoapp.exceptions.EntityNotFoundException;
import net.yigitak.todoapp.mappers.RecurrenceMapper;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.repositories.RecurrenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecurrenceServiceTest {

//  private static final String OWNER_ID = "owner-123";
//  private static final String REC_ID = "rec-456";
//
//  @Mock private RecurrenceRepository recurrenceRepository;
//
//  @Mock private RecurrenceMapper recurrenceMapper;
//
//  @InjectMocks private RecurrenceService recurrenceService;
//
//  @Test
//  void getAllRecurrencesByOwnerId_returnsRepositoryList() {
//    Recurrence r1 = new Recurrence();
//    r1.setId("a");
//    Recurrence r2 = new Recurrence();
//    r2.setId("b");
//
//    when(recurrenceRepository.findAllByOwnerId(OWNER_ID)).thenReturn(List.of(r1, r2));
//
//    List<Recurrence> result = recurrenceService.getAllRecurrencesByOwnerId(OWNER_ID);
//
//    assertThat(result).containsExactly(r1, r2);
//    verify(recurrenceRepository).findAllByOwnerId(OWNER_ID);
//  }
//
//  @Test
//  void createRecurrence_mapsDtoAndSaves() {
//    CreateRecurrenceDto dto = new CreateRecurrenceDto();
//    // populate dto as needed, e.g. dto.setName("Weekly");
//    Recurrence toSave = new Recurrence();
//    Recurrence saved = new Recurrence();
//    saved.setId("new-id");
//
//    when(recurrenceMapper.toEntity(dto, OWNER_ID)).thenReturn(toSave);
//    when(recurrenceRepository.save(toSave)).thenReturn(saved);
//
//    Recurrence result = recurrenceService.createRecurrence(dto, OWNER_ID);
//
//    assertThat(result).isSameAs(saved);
//    verify(recurrenceMapper).toEntity(dto, OWNER_ID);
//    verify(recurrenceRepository).save(toSave);
//  }
//
//  @Test
//  void getRecurrenceByIdAndOwnerId_existing_returnsRecurrence() {
//    Recurrence found = new Recurrence();
//    found.setId(REC_ID);
//
//    when(recurrenceRepository.findByIdAndOwnerId(REC_ID, OWNER_ID)).thenReturn(Optional.of(found));
//
//    Recurrence result = recurrenceService.getRecurrenceByIdAndOwnerId(REC_ID, OWNER_ID);
//
//    assertThat(result).isSameAs(found);
//    verify(recurrenceRepository).findByIdAndOwnerId(REC_ID, OWNER_ID);
//  }
//
//  @Test
//  void getRecurrenceByIdAndOwnerId_missing_throwsEntityNotFound() {
//    when(recurrenceRepository.findByIdAndOwnerId(REC_ID, OWNER_ID)).thenReturn(Optional.empty());
//
//    assertThatThrownBy(() -> recurrenceService.getRecurrenceByIdAndOwnerId(REC_ID, OWNER_ID))
//        .isInstanceOf(EntityNotFoundException.class)
//        .hasMessageContaining("Recurrence with the ID " + REC_ID + " not found.");
//
//    verify(recurrenceRepository).findByIdAndOwnerId(REC_ID, OWNER_ID);
//  }
//
//  @Test
//  void deleteRecurrenceByIdAndOwnerId_delegatesToRepository() {
//    // no stub needed for void
//    recurrenceService.deleteRecurrenceByIdAndOwnerId(REC_ID, OWNER_ID);
//
//    verify(recurrenceRepository).deleteByIdAndOwnerId(REC_ID, OWNER_ID);
//  }
}
