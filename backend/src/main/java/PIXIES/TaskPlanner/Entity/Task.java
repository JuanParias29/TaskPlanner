package PIXIES.TaskPlanner.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una tarea en la aplicación de planificación de tareas.
 */
@Entity
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDIENTE; // Enum para el estado de la tarea (PENDIENTE o COMPLETADA), por defecto PENDIENTE


}
