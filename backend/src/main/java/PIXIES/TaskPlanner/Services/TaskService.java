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

    // Actualizar tarea
    public Task updateTask(long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));

        // Validar el título
        if (updatedTask.getTitle() == null || updatedTask.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título no puede estar vacío");
        }


        // Actualizar la tarea
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        Task savedTask = taskRepository.save(existingTask);
        System.out.println("Tarea actualizada exitosamente: " + savedTask.getTitle());
        return savedTask;
    }

    // Eliminar tarea
    public void deleteTask(long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            System.out.println("Tarea eliminada exitosamente: " + id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada");
        }
    }

    // Obtener tareas por estado
    public List<Task> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        System.out.println("Tareas recuperadas por estado (" + status + "): " + tasks.size());
        return tasks;
    }
}