package ik.koresh.TodolistSpringRest.validation;

import ik.koresh.TodolistSpringRest.services.TasksService;
import ik.koresh.TodolistSpringRest.validation.annotation.DuplicateDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DuplicateDescriptionValidator implements ConstraintValidator<DuplicateDescription, String> {
    private final TasksService tasksService;

    public DuplicateDescriptionValidator(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return tasksService.findOne(value).isEmpty();
    }
}
