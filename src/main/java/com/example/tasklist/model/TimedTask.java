package com.example.tasklist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.tasklist.dto.TaskDTO;
import com.example.tasklist.dto.TimedTaskDTO;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("TIMED")
@Getter
@Setter
@NoArgsConstructor
public class TimedTask extends Task {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean active;

    public TimedTask(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(title, description);
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = isCurrentlyActive();
    }

    @Override
    public void updateFromDTO(TaskDTO taskDTO) {
        super.updateFromDTO(taskDTO);
        if (taskDTO instanceof TimedTaskDTO) {
            TimedTaskDTO timedTaskDTO = (TimedTaskDTO) taskDTO;
            this.startTime = timedTaskDTO.getStartTime();
            this.endTime = timedTaskDTO.getEndTime();
            this.active = isCurrentlyActive();
        }
    }

    public boolean isCurrentlyActive() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }
}
