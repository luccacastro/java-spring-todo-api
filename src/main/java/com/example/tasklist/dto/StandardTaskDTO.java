package com.example.tasklist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardTaskDTO extends TaskDTO {


    public StandardTaskDTO() {
        super();
    }

    public StandardTaskDTO(Long id, String title, String description) {
        super(id, title, description);
    }
}
