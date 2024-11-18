package PIXIES.TaskPlanner.Repository;

import PIXIES.TaskPlanner.Entity.Task;
import PIXIES.TaskPlanner.Entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.DayOfWeek;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);

}