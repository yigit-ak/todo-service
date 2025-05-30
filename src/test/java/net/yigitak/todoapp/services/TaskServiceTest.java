package net.yigitak.todoapp.services;


import net.yigitak.todoapp.mappers.TaskMapper;
import net.yigitak.todoapp.models.Task;
import net.yigitak.todoapp.repositories.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


//@ExtendWith(MockitoExtension.class)
@DisplayName( "TaskService Unit Tests" )
class TaskServiceTest {

  private static final String OWNER_ID = "owner-123";

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private TaskMapper taskMapper;

  @InjectMocks
  private TaskService taskService;


  @Nested
  @DisplayName( "getAllTasksByOwnerId" )
  class GetAllTasksByOwnerIdTests {

    @Test
    @DisplayName( "returns tasks when present" )
    void returnsTasksWhenPresent ( ) {
      // Arrange
      List< Task > mockTasks = List.of( new Task() , new Task() );
      when( taskRepository.findAllByOwnerId( OWNER_ID ) ).thenReturn( mockTasks );

      // Act
      List< Task > result = taskService.getAllTasksByOwnerId( OWNER_ID );

      // Assert
      assertThat( result ).isEqualTo( mockTasks );
      verify( taskRepository ).findAllByOwnerId( OWNER_ID );
    }


    @Test
    @DisplayName( "returns empty list when none found" )
    void returnsEmptyListWhenNoneFound ( ) {
      // Arrange
      when( taskRepository.findAllByOwnerId( OWNER_ID ) ).thenReturn( List.of() );

      // Act
      List< Task > result = taskService.getAllTasksByOwnerId( OWNER_ID );

      // Assert
      assertThat( result ).isEmpty();
      verify( taskRepository ).findAllByOwnerId( OWNER_ID );
    }

  }

}

}
