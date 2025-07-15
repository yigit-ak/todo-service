package net.yigitak.todoapp.services;

import net.yigitak.todoapp.config.TestContainerConfig;
import net.yigitak.todoapp.dto.CreateTaskDto;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestContainerConfig.class)
class TaskServiceIT {

    @Autowired
    private MongoDBContainer mongo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask_shouldSaveAndReturnTaskWithOwnerId() {
        // Given
        String ownerId = "user123";
        CreateTaskDto dto = CreateTaskDto.builder()
                .title("My Task")
                .description(Optional.of("This is a test task"))
                .build();

        // When
        Task createdTask = taskService.createTask(dto, ownerId);

        // Then
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo("My Task");
        assertThat(createdTask.getOwnerId()).isEqualTo(ownerId);
    }

}
