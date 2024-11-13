package com.example.tasklist.model;

import com.example.tasklist.dto.TaskDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.tasklist.dto.StandardTaskDTO;

@Entity
@DiscriminatorValue("STANDARD")
@Getter
@Setter
@NoArgsConstructor
public class StandardTask extends Task {

    public StandardTask(String title, String description) {
        super(title, description);
    }

    @Override
    public void updateFromDTO(TaskDTO taskDTO) {
        super.updateFromDTO(taskDTO);
        // No additional fields specific to StandardTask need to be updated here
    }
}
