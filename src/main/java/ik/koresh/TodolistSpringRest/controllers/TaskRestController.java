package ik.koresh.TodolistSpringRest.controllers;


import ik.koresh.TodolistSpringRest.dto.TaskDTO;
import ik.koresh.TodolistSpringRest.models.Task;
import ik.koresh.TodolistSpringRest.services.TasksService;
import ik.koresh.TodolistSpringRest.util.TaskErrorResponse;
import ik.koresh.TodolistSpringRest.exception.TaskNotCreatedException;
import ik.koresh.TodolistSpringRest.exception.TaskNotFoundException;


import ik.koresh.TodolistSpringRest.validation.TaskValidator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController // Controller + ResponseBody над каждым методом
@RequestMapping("/todolist")
public class TaskRestController {

    private final TaskValidator taskValidator;
    private final TasksService tasksService;
    private final ModelMapper modelMapper;

    public TaskRestController(TaskValidator taskValidator, TasksService tasksService, ModelMapper modelMapper1) {
        this.taskValidator = taskValidator;
        this.tasksService = tasksService;
        this.modelMapper = modelMapper1;
    }

    @GetMapping
    public List<TaskDTO> getTodolist(){
        return tasksService.findAll().stream().
                map(this::converToTaskDTO).
                collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable("id") int id){
        return converToTaskDTO(tasksService.findOneRest(id));
    }

    //todo: метод для проверки taskValidator --- Branch удалить потом
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody  @Validated Task task,
                                             BindingResult bindingResult){
        taskValidator.validate(task, bindingResult);

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

//        tasksService.save(convertToTask(taskDTO));
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

    private Task convertToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO converToTaskDTO(Task task){
        return modelMapper.map(task, TaskDTO.class);
    }

}
