package com.example.tasklist.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "queues")
@Getter
@Setter
@NoArgsConstructor
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Queue name is mandatory")
    @Size(max = 255, message = "Queue name must be less than 255 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Queue description must be less than 500 characters")
    private String description;

    @OneToMany
    @JoinColumn(name = "queue_id")
    private List<TimedTask> tasks;

    public Queue(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
