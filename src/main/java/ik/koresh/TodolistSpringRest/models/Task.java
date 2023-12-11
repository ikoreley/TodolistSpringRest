package ik.koresh.TodolistSpringRest.models;


import ik.koresh.TodolistSpringRest.validation.annotation.DuplicateDescription;
import ik.koresh.TodolistSpringRest.validation.annotation.StartSymbolUpperCase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDateTime;


@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Description should be between 2 and 30 characters")
    @StartSymbolUpperCase
//    @DuplicateDescription //todo: not working
    private String description;


    @Enumerated(EnumType.STRING)
    private Status status;

    @Min(value = 0, message = "Age should be greater than 0")
    private int deadline;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "create_who")
    private String createdWho;


    public Task() {}


}
