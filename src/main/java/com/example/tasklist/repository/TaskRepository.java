package com.example.tasklist.repository;

import com.example.tasklist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Find all completed tasks
//    List<Task> findByIsCompletedTrue();
}