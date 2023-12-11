package ik.koresh.TodolistSpringRest.dto;

import ik.koresh.TodolistSpringRest.models.Status;
import ik.koresh.TodolistSpringRest.validation.annotation.DuplicateDescription;
import ik.koresh.TodolistSpringRest.validation.annotation.StartSymbolUpperCase;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TaskDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Description should be between 2 and 30 characters")
    @StartSymbolUpperCase
    @DuplicateDescription
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Min(value = 0, message = "Age should be greater than 0")
    private int deadline;

    private LocalDateTime createdAt;
}
