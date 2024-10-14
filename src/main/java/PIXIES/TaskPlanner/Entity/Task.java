package PIXIES.TaskPlanner.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data // Esto es opcional pueden usarlo o crear los getter y setters
@NoArgsConstructor

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String status;


    /*
    posibles funcionalidades futuras
    private String priority;
    private String date;
    private String category;
     */

    /*@ManyToOne
    @JoinColumn(name = "user")
    private Zone zone;
    */
}