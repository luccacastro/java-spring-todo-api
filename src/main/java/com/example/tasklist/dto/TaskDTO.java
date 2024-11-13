package com.example.tasklist.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "task_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StandardTaskDTO.class, name = "standardTasks"),
        @JsonSubTypes.Type(value = TimedTaskDTO.class, name = "timedTasks"),
        @JsonSubTypes.Type(value = DueDateTaskDTO.class, name = "dueDateTasks")
})
public abstract class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private boolean completed;

    public TaskDTO(Long id, String title, String description) {
        this.title = title;
        this.description = description;
        this.id = id;
//        this.completed = false;
    }

    public TaskDTO() {}

    protected void updateFromDTO(TaskDTO taskDTO) {
    }
}
