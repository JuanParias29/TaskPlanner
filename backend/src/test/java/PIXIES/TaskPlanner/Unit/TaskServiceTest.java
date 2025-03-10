package PIXIES.TaskPlanner.Unit;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import PIXIES.TaskPlanner.Repository.TaskRepository;
import PIXIES.TaskPlanner.Services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void testCreateTask_Success() {
        Task task = new Task();
        task.setTitle("Test Task");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertEquals("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testCreateTask_EmptyTitle() {
        Task task = new Task();
        task.setTitle("");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> taskService.createTask(task));

        assertEquals("El título no puede estar vacío", exception.getReason());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testUpdateTask_Success() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Existing Task");

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("Updated Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void testUpdateTask_NotFound() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");

        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(ResponseStatusException.class, () -> taskService.updateTask(1L, updatedTask));

        assertEquals("Tarea no encontrada", exception.getReason());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testUpdateTask_EmptyTitle() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Existing Task");

        Task updatedTask = new Task();
        updatedTask.setTitle("");

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(existingTask));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> taskService.updateTask(1L, updatedTask));

        assertEquals("El título no puede estar vacío", exception.getReason());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTask_Success() {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> taskService.deleteTask(1L));

        assertEquals("Tarea no encontrada", exception.getReason());
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void testGetTasksByStatus() {
        List<Task> tasks = List.of(new Task(), new Task());

        when(taskRepository.findByStatus(any(TaskStatus.class))).thenReturn(tasks);

        List<Task> result = taskService.getTasksByStatus(TaskStatus.COMPLETADO);

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findByStatus(TaskStatus.COMPLETADO);
    }
}

// ejemplod e commit enlazado a issue
