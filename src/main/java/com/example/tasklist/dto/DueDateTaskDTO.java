package com.example.tasklist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DueDateTaskDTO extends TaskDTO {

    @NotNull(message = "Due date is mandatory")
    private LocalDateTime dueDate;

    public DueDateTaskDTO() {
        super();
    }

    @Override
    public void updateFromDTO(TaskDTO taskDTO) {
        super.updateFromDTO(taskDTO);
        if (taskDTO instanceof DueDateTaskDTO) {
            this.dueDate = ((DueDateTaskDTO) taskDTO).getDueDate();
        }
    }

    public DueDateTaskDTO(Long id, String title, String description, LocalDateTime dueDate) {
        super(id, title, description);
        this.dueDate = dueDate;
    }
}
