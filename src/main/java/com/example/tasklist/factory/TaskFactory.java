package com.example.tasklist.factory;

import com.example.tasklist.model.StandardTask;
import com.example.tasklist.model.DueDateTask;
import com.example.tasklist.model.TimedTask;
import com.example.tasklist.model.Task;
import com.example.tasklist.dto.StandardTaskDTO;
import com.example.tasklist.dto.DueDateTaskDTO;
import com.example.tasklist.dto.TimedTaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import jakarta.validation.Validator;
import jakarta.validation.Validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TaskFactory {

    private final ObjectMapper objectMapper;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    public TaskFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Task instantiateTask(Map<String, Object> requestBody) {
        String taskType = (String) requestBody.get("task_type");
        if (taskType == null) {
            throw new IllegalArgumentException("Task type is required");
        }
        return switch (taskType) {
            case "standardTasks" -> createStandardTask(requestBody);
            case "dueDateTasks" -> createDueDateTask(requestBody);
            case "timedTasks" -> createTimedTask(requestBody);
            default -> throw new IllegalArgumentException("Invalid task type: " + taskType);
        };
    }

    private StandardTask createStandardTask(Map<String, Object> requestBody) {
        StandardTaskDTO standardTaskDTO = objectMapper.convertValue(requestBody, StandardTaskDTO.class);
        validateDTO(standardTaskDTO);
        return new StandardTask(standardTaskDTO.getTitle(), standardTaskDTO.getDescription());
    }

    private DueDateTask createDueDateTask(Map<String, Object> requestBody) {
        DueDateTaskDTO dueDateTaskDTO = objectMapper.convertValue(requestBody, DueDateTaskDTO.class);
        validateDTO(dueDateTaskDTO);
        return new DueDateTask(dueDateTaskDTO.getTitle(), dueDateTaskDTO.getDescription(), dueDateTaskDTO.getDueDate());
    }

    private TimedTask createTimedTask(Map<String, Object> requestBody) {
        TimedTaskDTO timedTaskDTO = objectMapper.convertValue(requestBody, TimedTaskDTO.class);
        validateDTO(timedTaskDTO);
        return new TimedTask(timedTaskDTO.getTitle(), timedTaskDTO.getDescription(), timedTaskDTO.getStartTime(), timedTaskDTO.getEndTime());
    }

    private <T> void validateDTO(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<T> violation : violations) {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(fieldName, errorMessage);
            }
            throw new IllegalArgumentException(errors.toString());
        }
    }
}
