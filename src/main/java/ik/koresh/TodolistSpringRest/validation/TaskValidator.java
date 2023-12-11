package ik.koresh.TodolistSpringRest.validation;

import ik.koresh.TodolistSpringRest.dto.TaskDTO;
import ik.koresh.TodolistSpringRest.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class TaskValidator implements Validator {
    private final TasksService tasksService;

    @Autowired
    public TaskValidator(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskDTO taskDTO = (TaskDTO) target;

        if(tasksService.findOne(taskDTO.getDescription()).isPresent()){
            errors.rejectValue("description", "", "It's description exist already!");
        }


    }
}
