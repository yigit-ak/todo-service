package net.yigitak.todoapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.yigitak.todoapp.dto.CreateRecurrenceDto;
import net.yigitak.todoapp.models.Recurrence;
import net.yigitak.todoapp.services.RecurrenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RecurrenceControllerTest {

//  private static final String BASE_URL = "/api/v1/recurrences";
//  private static final String USER_HEADER = "X-User-Id";
//  private static final String USER_ID = "user123";
//
//  @Mock private RecurrenceService recurrenceService;
//
//  @InjectMocks private RecurrenceController controller;
//
//  private MockMvc mockMvc;
//  private ObjectMapper objectMapper;
//
//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this);
//    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    objectMapper = new ObjectMapper();
//  }
//
//  @Test
//  void getAllRecurrences_shouldReturnList() throws Exception {
//    Recurrence r = new Recurrence();
//    r.setId("rec1");
//    // (set other fields if needed)
//    List<Recurrence> recList = Collections.singletonList(r);
//
//    when(recurrenceService.getAllRecurrencesByOwnerId(USER_ID)).thenReturn(recList);
//
//    mockMvc
//        .perform(get(BASE_URL).header(USER_HEADER, USER_ID))
//        .andExpect(status().isOk())
//        .andExpect(content().json(objectMapper.writeValueAsString(recList)));
//
//    verify(recurrenceService).getAllRecurrencesByOwnerId(USER_ID);
//  }
//
//  @Test
//  void getRecurrenceById_shouldReturnOne() throws Exception {
//    String recId = "rec42";
//    Recurrence r = new Recurrence();
//    r.setId(recId);
//    // (set other fields)
//
//    when(recurrenceService.getRecurrenceByIdAndOwnerId(recId, USER_ID)).thenReturn(r);
//
//    mockMvc
//        .perform(get(BASE_URL + "/" + recId).header(USER_HEADER, USER_ID))
//        .andExpect(status().isOk())
//        .andExpect(content().json(objectMapper.writeValueAsString(r)));
//
//    verify(recurrenceService).getRecurrenceByIdAndOwnerId(recId, USER_ID);
//  }
//
//  @Test
//  void createRecurrence_shouldReturnCreatedAndLocation() throws Exception {
//    // we don’t know dto fields; stub any()
//    CreateRecurrenceDto dto = new CreateRecurrenceDto("Task",null,null,1);
//    Recurrence created = new Recurrence();
//    created.setId("newRec");
//
//    when(recurrenceService.createRecurrence(any(CreateRecurrenceDto.class), eq(USER_ID)))
//        .thenReturn(created);
//
//    mockMvc
//        .perform(
//            post(BASE_URL)
//                .header(USER_HEADER, USER_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(dto)))
//        .andExpect(status().isCreated())
//        .andExpect(header().string("Location", "http://localhost" + BASE_URL + "/newRec"));
//
//    verify(recurrenceService).createRecurrence(any(CreateRecurrenceDto.class), eq(USER_ID));
//  }
//
//  @Test
//  void deleteRecurrenceById_shouldReturnNoContent() throws Exception {
//    String recId = "toDelete";
//
//    // no need to stub void; just perform
//    mockMvc
//        .perform(delete(BASE_URL + "/" + recId).header(USER_HEADER, USER_ID))
//        .andExpect(status().isNoContent());
//
//    verify(recurrenceService).deleteRecurrenceByIdAndOwnerId(recId, USER_ID);
//  }
}
