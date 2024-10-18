package PIXIES.TaskPlanner.Repository;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaz de repositorio para la entidad Task.
 * Extiende JpaRepository para proporcionar operaciones CRUD estándar.
 * Incluye un metodo para encontrar tareas por estado
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}