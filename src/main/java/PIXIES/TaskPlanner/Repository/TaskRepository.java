package PIXIES.TaskPlanner.Repository;

import PIXIES.TaskPlanner.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
