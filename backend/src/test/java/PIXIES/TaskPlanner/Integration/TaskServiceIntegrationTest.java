package PIXIES.TaskPlanner.Integration;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import PIXIES.TaskPlanner.Repository.TaskRepository;
import PIXIES.TaskPlanner.Services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveTask() {
        Task task = new Task();
        task.setTitle("Integración Tarea");
        task.setDescription("Descripción de integración");
        task.setStatus(TaskStatus.PENDIENTE);

        // Crear tarea
        Task createdTask = taskService.createTask(task);

        // Recuperar tarea
        Task retrievedTask = taskRepository.findById(createdTask.getId()).orElse(null);
        assertNotNull(retrievedTask);
        assertEquals("Integración Tarea", retrievedTask.getTitle());
        System.out.println("Prueba de integración de creación y recuperación de tarea: éxito");
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.setTitle("Tarea Inicial");
        task.setDescription("Descripción inicial");
        task.setStatus(TaskStatus.PENDIENTE);

        // Crear tarea
        Task createdTask = taskService.createTask(task);

        // Actualizar tarea
        createdTask.setTitle("Tarea Actualizada");
        createdTask.setDescription("Descripción actualizada");
        createdTask.setStatus(TaskStatus.COMPLETADA);

        Task result = taskService.updateTask(createdTask.getId(), createdTask);
        assertEquals("Tarea Actualizada", result.getTitle());
        assertEquals(TaskStatus.COMPLETADA, result.getStatus());
        System.out.println("Prueba de integración de actualización de tarea: éxito");
    }

    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setTitle("Tarea a Eliminar");
        task.setDescription("Descripción a eliminar");
        task.setStatus(TaskStatus.PENDIENTE);

        // Crear tarea
        Task createdTask = taskService.createTask(task);

        // Eliminar tarea
        taskService.deleteTask(createdTask.getId());

        // Validar eliminación
        assertFalse(taskRepository.findById(createdTask.getId()).isPresent());
        System.out.println("Prueba de integración de eliminación de tarea: éxito");
    }

    @Test
    void testGetTasksByStatus() {
        Task task1 = new Task();
        task1.setTitle("Tarea Pendiente");
        task1.setDescription("Descripción pendiente");
        task1.setStatus(TaskStatus.PENDIENTE);

        Task task2 = new Task();
        task2.setTitle("Tarea Completada");
        task2.setDescription("Descripción completada");
        task2.setStatus(TaskStatus.COMPLETADA);

        // Crear tareas
        taskService.createTask(task1);
        taskService.createTask(task2);

        // Recuperar tareas por estado
        List<Task> pendingTasks = taskService.getTasksByStatus(TaskStatus.PENDIENTE);
        List<Task> completedTasks = taskService.getTasksByStatus(TaskStatus.COMPLETADA);

        assertEquals(1, pendingTasks.size());
        assertEquals("Tarea Pendiente", pendingTasks.getFirst().getTitle());
        assertEquals(1, completedTasks.size());
        assertEquals("Tarea Completada", completedTasks.getFirst().getTitle());
        System.out.println("Prueba de integración de recuperación de tareas por estado: éxito");
    }
}