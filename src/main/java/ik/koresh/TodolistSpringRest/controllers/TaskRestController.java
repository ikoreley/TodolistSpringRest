package ik.koresh.TodolistSpringRest.controllers;



import ik.koresh.TodolistSpringRest.models.Task;
import ik.koresh.TodolistSpringRest.services.TasksService;
import ik.koresh.TodolistSpringRest.util.TaskErrorResponse;
import ik.koresh.TodolistSpringRest.exception.TaskNotCreatedException;
import ik.koresh.TodolistSpringRest.exception.TaskNotFoundException;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController // Controller + ResponseBody над каждым методом
@RequestMapping("/todolist")
public class TaskRestController {

    private final TasksService tasksService;

    public TaskRestController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public List<Task> getTodolist(){
        return tasksService.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") int id){
        return tasksService.findOneRest(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody  @Validated Task task,
                                             BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new TaskNotCreatedException(errorMsg.toString());
        }

        tasksService.save(task);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> handleException(TaskNotFoundException e){
        TaskErrorResponse response = new TaskErrorResponse(
                "Task with ID wasn't found!",
                new Date(System.currentTimeMillis())
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> handleException(TaskNotCreatedException e){
        TaskErrorResponse response = new TaskErrorResponse(
                e.getMessage(),
                new Date(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/del{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        tasksService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
