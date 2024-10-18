package PIXIES.TaskPlanner.Integration;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import PIXIES.TaskPlanner.Repository.TaskRepository;
import PIXIES.TaskPlanner.Services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    //Prueba para crear una tarea.
    @Test
    void testCreateTask() {

        //Crear tarea
        Task task = new Task();
        task.setTitle("Integration Task");

        Task createdTask = taskService.createTask(task);

        assertEquals("Integration Task", createdTask.getTitle());

        taskRepository.delete(createdTask);
    }

    //Excepción al intentar crear una tarea con un título vacío.
    @Test
    void testCreateTaskWithEmptyTitle() {

        //Crear una tarea
        Task task = new Task();
        task.setTitle("");

        //Excepcion
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> taskService.createTask(task));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("El título no puede estar vacío", exception.getReason());
    }


    //Prueba para actualizar una tarea.
    @Test
    void testUpdateTask() {

        //Creacion de la tarea
        Task task = new Task();
        task.setTitle("Integration Task");
        task = taskRepository.save(task);

        //Actualizacion de la tarea
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Integration Task");

        Task result = taskService.updateTask(task.getId(), updatedTask);

        assertEquals("Updated Integration Task", result.getTitle());

        taskRepository.delete(result);
    }

    // Excepción al intentar actualizar una tarea con un título vacío.
    @Test
    void testUpdateTaskWithEmptyTitle() {

        //Crear tarea
        Task task = new Task();
        task.setTitle("Integration Task");
        task = taskRepository.save(task);

        //Actualizar tarea con titulo vacio
        Task updatedTask = new Task();
        updatedTask.setTitle("");

        //excepcion
        Task finalTask = task;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> taskService.updateTask(finalTask.getId(), updatedTask));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("El título no puede estar vacío", exception.getReason());

        taskRepository.delete(task);
    }

    // Prueba para eliminar una tarea
    @Test
    void testDeleteTask() {

        //Crear Tarea
        Task task = new Task();
        task.setTitle("Integration Task");
        task = taskRepository.save(task);

        taskService.deleteTask(task.getId());

        assertFalse(taskRepository.findById(task.getId()).isPresent()); // Verificar eliminación
    }

    //Excepción al intentar eliminar una tarea que no existe.
    @Test
    void testDeleteNonExistingTask() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            taskService.deleteTask(999L); // ID inexistente
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Tarea no encontrada", exception.getReason());
    }

    //Prueba para obtener tareas por estado (pendiente o completada).
    @Test
    void testGetTasksByStatus() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setStatus(TaskStatus.PENDIENTE);
        taskRepository.save(task1); // Guardar tarea pendiente

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setStatus(TaskStatus.COMPLETADO);
        taskRepository.save(task2); // Guardar tarea completada

        List<Task> pendingTasks = taskService.getTasksByStatus(TaskStatus.PENDIENTE);
        List<Task> completedTasks = taskService.getTasksByStatus(TaskStatus.COMPLETADO);

        // Verificar que las tareas se han recuperado correctamente por su estado
        assertTrue(pendingTasks.stream().anyMatch(task -> task.getTitle().equals("Task 1")));
        assertTrue(completedTasks.stream().anyMatch(task -> task.getTitle().equals("Task 2")));

        taskRepository.delete(task1);
        taskRepository.delete(task2);
    }
}
