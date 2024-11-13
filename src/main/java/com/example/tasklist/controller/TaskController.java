package com.example.tasklist.controller;

import com.example.tasklist.factory.TaskFactory;
import com.example.tasklist.dto.TaskDTO;
import com.example.tasklist.model.DueDateTask;
import com.example.tasklist.model.StandardTask;
import com.example.tasklist.model.Task;
import com.example.tasklist.model.TimedTask;
import com.example.tasklist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskFactory taskFactory;

    // Get all tasks
    @GetMapping
    public ResponseEntity<Map<String, List<TaskDTO>>> getAllTasks() {
        Map<String, List<TaskDTO>> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", String.format("Task with ID %d not found", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        successResponse.put("data", task);
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(
            @RequestBody Map<String, Object> requestBody) {
        try {
            Task task = taskFactory.instantiateTask(requestBody);
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.ok(createdTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDetails) {
        Task existingTask = taskService.getTaskById(id);
        if (existingTask == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", String.format("Task with ID %d not found", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        if (existingTask instanceof StandardTask) {
            ((StandardTask) existingTask).updateFromDTO(taskDetails);
        } else if (existingTask instanceof TimedTask) {
            ((TimedTask) existingTask).updateFromDTO(taskDetails);
        } else if (existingTask instanceof DueDateTask) {
            ((DueDateTask) existingTask).updateFromDTO(taskDetails);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Invalid task type for update");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Task updatedTask = taskService.updateTask(existingTask);

        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        successResponse.put("data", updatedTask);
        return ResponseEntity.ok(successResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable Long id) {
        Task existingTask = taskService.getTaskById(id);

        if (existingTask == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", String.format("Task with ID %d not found", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        taskService.deleteTask(id);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        successResponse.put("message", String.format("Task with ID %d successfully deleted", id));
        return ResponseEntity.ok(successResponse);
    }

}
