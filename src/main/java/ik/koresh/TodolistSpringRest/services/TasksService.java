package ik.koresh.TodolistSpringRest.services;



import ik.koresh.TodolistSpringRest.models.Status;
import ik.koresh.TodolistSpringRest.models.Task;
import ik.koresh.TodolistSpringRest.repositories.TasksRepository;
import ik.koresh.TodolistSpringRest.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TasksService {

    private final TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> findAll(){
        return tasksRepository.findAll();
    }

    public Task findOne(int id){
        Optional<Task> foundTask = tasksRepository.findById(id);
        return foundTask.orElse(null);
    }

    //todo: проверить работу
    public Task findOne(String description){
        Optional<Task> foundTask = tasksRepository.findByDescription(description);
        return foundTask.orElse(null);
    }

    public Task findOneRest(int id){
        Optional<Task> foundTask = tasksRepository.findById(id);
        return foundTask.orElseThrow(TaskNotFoundException::new);
    }

    @Transactional
    public void save(Task task){
        if (task.getStatus()==null){
            task.setStatus(Status.EMPTY);
        }
        tasksRepository.save(task);
    }

    @Transactional
    public void update(int id, Task updateTask){
        updateTask.setId(id);
        tasksRepository.save(updateTask);
    }

    @Transactional
    public void delete(int id){
        findOneRest(id);
        tasksRepository.deleteById(id);
    }
}
