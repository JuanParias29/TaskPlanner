package PIXIES.TaskPlanner.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.DayOfWeek;

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
    private TaskStatus status = TaskStatus.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek day;

    @Column(nullable = true)
    private Integer week;

}
