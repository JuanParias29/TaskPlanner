package PIXIES.TaskPlanner.Services;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import PIXIES.TaskPlanner.Entity.User;
import PIXIES.TaskPlanner.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByStatusAndUser(TaskStatus status, User user) {
        return taskRepository.findByStatusAndUser(status, user);
    }
}