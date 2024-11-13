package com.example.tasklist.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("DUE_DATE")
@Getter
@Setter
@NoArgsConstructor
public class DueDateTask extends Task {

    @Column
    private LocalDateTime dueDate;

    public DueDateTask(String title, String description, LocalDateTime dueDate) {
        super(title, description);
        this.dueDate = dueDate;
    }
}
