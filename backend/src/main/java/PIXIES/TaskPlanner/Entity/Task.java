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
    private TaskStatus status; // Enum para el estado de la tarea (PENDIENTE o COMPLETADA)

    /*
    Posibles funcionalidades futuras:
    private String priority;
    private String date;
    private String category;
     */

    /*@ManyToOne
    @JoinColumn(name = "user")
    private Zone zone;
    */
}