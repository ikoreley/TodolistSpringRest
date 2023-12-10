package ik.koresh.TodolistSpringRest.models;


import ik.koresh.TodolistSpringRest.validation.annotation.StartSymbolUpperCase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Description should be between 2 and 30 characters")
    @StartSymbolUpperCase
//    @DuplicateDescription
    private String description;


    @Enumerated(EnumType.STRING)
    private Status status;

    @Min(value = 0, message = "Age should be greater than 0")
    private int deadline;

    public Task() {}

    public Task(String description, Status  status, int deadline) {
        this.description = description;
        this.status = status;
        this.deadline = deadline;
    }
}
