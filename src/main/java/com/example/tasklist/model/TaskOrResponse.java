package com.example.tasklist.model;

import com.example.tasklist.model.Task;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
public class TaskOrResponse {
    private Task task;
    private ResponseEntity<Map<String, Object>> response;

    public TaskOrResponse(Task task) {
        this.task = task;
    }

    public TaskOrResponse(ResponseEntity<Map<String, Object>> response) {
        this.response = response;
    }

    public boolean hasTask() {
        return task != null;
    }

}
