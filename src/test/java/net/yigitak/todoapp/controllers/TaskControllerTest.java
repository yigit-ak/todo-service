package net.yigitak.todoapp.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import net.yigitak.todoapp.dto.CreateSubtaskDto;
import net.yigitak.todoapp.dto.CreateTaskDto;
import net.yigitak.todoapp.exceptions.EntityNotFoundException;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.services.DateService;
import net.yigitak.todoapp.services.TaskService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("TaskController Unit Tests") @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

  private static final String USER_ID_HEADER = "X-User-Id";
  private static final String BASE_URI = "/api/v1/tasks";

  private static final String USER_ID = (new ObjectId()).toHexString();
  private static final String TASK_ID = (new ObjectId()).toHexString();
  private static final String SUBTASK_ID = (new ObjectId()).toHexString();

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper mapper;

  @Mock private TaskService taskService;

  @Mock private DateService dateService;

  private String asJson(Object o) throws Exception {
    return mapper.writeValueAsString(o);
  }

  @Nested
  @DisplayName("GET /api/v1/tasks")
  class GetAllTasks {

    @Test
    void when_tasks_exist_returns_200_and_list() throws Exception {
      Task task = new Task();
      task.setId(TASK_ID);
      when(taskService.getAllTasksByOwnerId(USER_ID)).thenReturn(List.of(task));

      mockMvc
          .perform(get(BASE_URI).header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id").value(TASK_ID));

      verify(taskService).getAllTasksByOwnerId(USER_ID);
    }

    @Test
    void when_no_task_returns_200_and_empty_array() throws Exception {
      when(taskService.getAllTasksByOwnerId(USER_ID)).thenReturn(Collections.emptyList());

      mockMvc
          .perform(get(BASE_URI).header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isOk())
          .andExpect(content().json("[]"));
    }
  }

  @Nested
  @DisplayName("POST /api/v1/tasks")
  class CreateTask {

    @Test
    void when_valid_request_returns_201_and_location_header() throws Exception {
      CreateTaskDto dto = new CreateTaskDto("My task", null, null, null, null);
      Task created = new Task();
      created.setId(TASK_ID);
      when(taskService.createTask(dto, USER_ID)).thenReturn(created);

      mockMvc
          .perform(
              post(BASE_URI)
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(USER_ID_HEADER, USER_ID)
                  .content(asJson(dto)))
          .andExpect(status().isCreated())
          .andExpect(header().string("Location", BASE_URI + "/" + TASK_ID));

      verify(taskService).createTask(dto, USER_ID);
    }

    @Test
    void when_service_rejects_request_returns_400() throws Exception {
      CreateTaskDto dto = new CreateTaskDto("Bad", null, null, null, null);
      when(taskService.createTask(dto, USER_ID)).thenThrow(new IllegalArgumentException("bad"));

      mockMvc
          .perform(
              post(BASE_URI)
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(USER_ID_HEADER, USER_ID)
                  .content(asJson(dto)))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  @DisplayName("GET /api/v1/tasks/{id}")
  class GetTaskById {

    @Test
    void when_task_exists_returns_200_and_body() throws Exception {
      Task task = new Task();
      task.setId(TASK_ID);
      when(taskService.getTaskByIdAndOwnerId(TASK_ID, USER_ID)).thenReturn(task);

      mockMvc
          .perform(get(BASE_URI + "/" + TASK_ID).header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(TASK_ID));
    }

    @Test
    void when_task_not_found_returns_404() throws Exception {
      when(taskService.getTaskByIdAndOwnerId(TASK_ID, USER_ID))
          .thenThrow(new EntityNotFoundException("not found"));

      mockMvc
          .perform(get(BASE_URI + "/" + TASK_ID).header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("DELETE /api/v1/tasks/{id}")
  class DeleteTask {

    @Test
    void when_task_deleted_returns_204() throws Exception {
      doNothing().when(taskService).deleteTaskByIdAndOwnerId(TASK_ID, USER_ID);

      mockMvc
          .perform(delete(BASE_URI + "/" + TASK_ID).header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isNoContent());
    }

    @Test
    void when_task_not_found_returns_404() throws Exception {
      doThrow(new EntityNotFoundException("not found"))
          .when(taskService)
          .deleteTaskByIdAndOwnerId(TASK_ID, USER_ID);

      mockMvc
          .perform(delete(BASE_URI + "/" + TASK_ID).header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("POST /api/v1/tasks/{id}/subtasks")
  class AddSubtask {

    @Test
    void when_valid_request_returns_201_and_location() throws Exception {
      CreateSubtaskDto dto = new CreateSubtaskDto("Child", null);
      doNothing().when(taskService).addSubtaskToTaskByOwnerId(TASK_ID, dto, USER_ID);

      mockMvc
          .perform(
              post(BASE_URI + "/" + TASK_ID + "/subtasks")
                  .header(USER_ID_HEADER, USER_ID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(asJson(dto)))
          .andExpect(status().isCreated())
          .andExpect(header().string("Location", BASE_URI + "/tasks/" + TASK_ID));
    }

    @Test
    void when_parent_task_missing_returns_404() throws Exception {
      CreateSubtaskDto dto = new CreateSubtaskDto("Child", null);
      doThrow(new EntityNotFoundException("parent missing"))
          .when(taskService)
          .addSubtaskToTaskByOwnerId(TASK_ID, dto, USER_ID);

      mockMvc
          .perform(
              post(BASE_URI + "/" + TASK_ID + "/subtasks")
                  .header(USER_ID_HEADER, USER_ID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(asJson(dto)))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("DELETE /api/v1/tasks/{taskId}/subtasks/{subtaskId}")
  class DeleteSubtask {

    @Test
    void when_subtask_deleted_returns_204() throws Exception {
      doNothing()
          .when(taskService)
          .deleteSubtaskByParentTaskIdAndOwnerId(TASK_ID, SUBTASK_ID, USER_ID);

      mockMvc
          .perform(
              delete(BASE_URI + "/" + TASK_ID + "/subtasks/" + SUBTASK_ID)
                  .header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isNoContent());
    }

    @Test
    void when_subtask_or_parent_missing_returns_404() throws Exception {
      doThrow(new EntityNotFoundException("missing"))
          .when(taskService)
          .deleteSubtaskByParentTaskIdAndOwnerId(TASK_ID, SUBTASK_ID, USER_ID);

      mockMvc
          .perform(
              delete(BASE_URI + "/" + TASK_ID + "/subtasks/" + SUBTASK_ID)
                  .header(USER_ID_HEADER, USER_ID))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("GET /api/v1/tasks/date?date={isoDate}")
  class GetTasksByDate {

    private final LocalDate DATE = LocalDate.of(2025, 5, 14);

    @Test
    void when_valid_date_returns_200_with_list() throws Exception {
      when(dateService.getAllTasksByAssignedDate(USER_ID, DATE))
          .thenReturn(Collections.emptyList());

      mockMvc
          .perform(
              get(BASE_URI + "/date").header(USER_ID_HEADER, USER_ID).param("date", DATE.toString()))
          .andExpect(status().isOk());
    }

    @Test
    void when_invalid_date_format_returns_400() throws Exception {
      mockMvc
          .perform(get(BASE_URI + "/date").header(USER_ID_HEADER, USER_ID).param("date", "14-05-2025"))
          .andExpect(status().isBadRequest());
    }
  }
}
