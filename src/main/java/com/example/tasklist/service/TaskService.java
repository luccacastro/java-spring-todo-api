package com.example.tasklist.service;

import com.example.tasklist.dto.DueDateTaskDTO;
import com.example.tasklist.dto.StandardTaskDTO;
import com.example.tasklist.dto.TaskDTO;
import com.example.tasklist.dto.TimedTaskDTO;
import com.example.tasklist.model.DueDateTask;
import com.example.tasklist.model.StandardTask;
import com.example.tasklist.model.Task;
import com.example.tasklist.model.TimedTask;
import com.example.tasklist.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;


    public Map<String, List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        Map<String, List<TaskDTO>> groupedTasks = tasks.stream()
                .collect(Collectors.groupingBy(
                        task -> {
                            return switch (task) {
                                case StandardTask standardTask -> "standardTasks";
                                case DueDateTask dueDateTask -> "dueDateTasks";
                                case TimedTask timedTask -> "timedTasks";
                                default -> "unknownTasks"; // Future-proof for new types
                            };
                        },
                        Collectors.mapping(this::mapToTaskDTO, Collectors.toList())
                ));

        groupedTasks.putIfAbsent("standardTasks", List.of());
        groupedTasks.putIfAbsent("dueDateTasks", List.of());
        groupedTasks.putIfAbsent("timedTasks", List.of());

        return groupedTasks;
    }


    private TaskDTO mapToTaskDTO(Task task) {
        return switch (task) {
            case StandardTask standardTask ->
                    new StandardTaskDTO(standardTask.getId(), standardTask.getTitle(),
                            standardTask.getDescription());
            case DueDateTask dueDateTask ->
                    new DueDateTaskDTO(dueDateTask.getId(), dueDateTask.getTitle(), dueDateTask.getDescription(),
                            dueDateTask.getDueDate());
            case TimedTask timedTask ->
                    new TimedTaskDTO(timedTask.getId(), timedTask.getTitle(), timedTask.getDescription(),
                            timedTask.getStartTime(), timedTask.getEndTime());
            default -> throw new IllegalArgumentException("Unknown task type: " + task.getClass().getName());
        };
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        logger.info("Creating new task: " + task.getTitle());
        logger.info(task.toString());
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
