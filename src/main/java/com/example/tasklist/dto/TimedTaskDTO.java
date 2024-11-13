package com.example.tasklist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TimedTaskDTO extends TaskDTO {

    @NotNull(message = "Start time is mandatory")
    private LocalDateTime startTime;

    @NotNull(message = "End time is mandatory")
    private LocalDateTime endTime;

    public TimedTaskDTO() {
        super();
    }

    public TimedTaskDTO(Long id, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(id , title, description);
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
