package PIXIES.TaskPlanner.Unit;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import PIXIES.TaskPlanner.Repository.TaskRepository;
import PIXIES.TaskPlanner.Services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        // Crear una nueva tarea con título
        Task task = new Task();
        task.setTitle("Nueva Tarea");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Validar que la tarea se crea correctamente
        Task createdTask = taskService.createTask(task);
        assertNotNull(createdTask);
        assertEquals("Nueva Tarea", createdTask.getTitle());
        System.out.println("Test crear tarea con título: éxito");
    }

    @Test
    void testCreateTaskWithoutTitle() {
        // Intentar crear una tarea sin título
        Task task = new Task();

        // Validar que se lanza una excepción por falta de título
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> taskService.createTask(task));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("El título no puede estar vacío", exception.getReason());
        System.out.println("Test crear tarea sin título: éxito");
    }

    @Test
    void testUpdateTask() {
        // Actualizar una tarea existente
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Tarea Existente");

        Task updatedTask = new Task();
        updatedTask.setTitle("Tarea Actualizada");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Validar que la tarea se actualiza correctamente
        Task result = taskService.updateTask(1L, updatedTask);
        assertNotNull(result);
        assertEquals("Tarea Actualizada", result.getTitle());
        System.out.println("Test actualizar tarea existente: éxito");
    }

    @Test
    void testUpdateTaskNotFound() {
        // Intentar actualizar una tarea que no existe
        Task updatedTask = new Task();
        updatedTask.setTitle("Tarea Actualizada");

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Validar que se lanza una excepción por tarea no encontrada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> taskService.updateTask(1L, updatedTask));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Tarea no encontrada", exception.getReason());
        System.out.println("Test actualizar tarea no encontrada: éxito");
    }

    @Test
    void testUpdateTaskWithoutTitle() {
        // Intentar actualizar una tarea sin título
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Tarea Existente");

        Task updatedTask = new Task();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        // Validar que se lanza una excepción por falta de título
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> taskService.updateTask(1L, updatedTask));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("El título no puede estar vacío", exception.getReason());
        System.out.println("Test actualizar tarea sin título: éxito");
    }

    @Test
    void testDeleteTask() {
        // Eliminar una tarea existente
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        // Validar que la tarea se elimina sin lanzar excepciones
        assertDoesNotThrow(() -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).delete(task);
        System.out.println("Test eliminar tarea existente: éxito");
    }

    @Test
    void testDeleteTaskNotFound() {
        // Intentar eliminar una tarea que no existe
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Validar que se lanza una excepción por tarea no encontrada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> taskService.deleteTask(1L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Tarea no encontrada", exception.getReason());
        System.out.println("Test eliminar tarea no encontrada: éxito");
    }

    @Test
    void testGetTasksByStatus() {
        // Obtener tareas por estado (PENDIENTE y COMPLETADA)
        Task task1 = new Task();
        task1.setStatus(TaskStatus.PENDIENTE);

        Task task2 = new Task();
        task2.setStatus(TaskStatus.COMPLETADA);

        when(taskRepository.findByStatus(TaskStatus.PENDIENTE)).thenReturn(List.of(task1));
        when(taskRepository.findByStatus(TaskStatus.COMPLETADA)).thenReturn(List.of(task2));

        // Validar que se obtienen correctamente las tareas pendientes
        List<Task> pendingTasks = taskService.getTasksByStatus(TaskStatus.PENDIENTE);
        assertEquals(1, pendingTasks.size());
        assertEquals(TaskStatus.PENDIENTE, pendingTasks.getFirst().getStatus());
        System.out.println("Test obtener tareas pendientes: éxito");

        // Validar que se obtienen correctamente las tareas completadas
        List<Task> completedTasks = taskService.getTasksByStatus(TaskStatus.COMPLETADA);
        assertEquals(1, completedTasks.size());
        assertEquals(TaskStatus.COMPLETADA, completedTasks.getFirst().getStatus());
        System.out.println("Test obtener tareas completadas: éxito");
    }
}
