package PIXIES.TaskPlanner.Services;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import PIXIES.TaskPlanner.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Añadir tarea con validación de título
    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título no puede estar vacío");
        }
        Task createdTask = taskRepository.save(task);
        System.out.println("Tarea creada exitosamente: " + createdTask.getTitle());
        return createdTask;
    }

    // Editar tarea
    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));

        if (updatedTask.getTitle() == null || updatedTask.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título no puede estar vacío");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus() != null ? updatedTask.getStatus() : TaskStatus.PENDIENTE);

        Task savedTask = taskRepository.save(task);
        System.out.println("Tarea actualizada exitosamente: " + savedTask.getTitle());
        return savedTask;
    }

    // Eliminar tarea por ID
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));
        taskRepository.delete(task);
        System.out.println("Tarea eliminada exitosamente: " + id);
    }

    // Filtrar tareas por estado (completadas o pendientes)
    public List<Task> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        System.out.println("Tareas obtenidas por estado: " + status + " - Cantidad: " + tasks.size());
        return tasks;
    }
}

