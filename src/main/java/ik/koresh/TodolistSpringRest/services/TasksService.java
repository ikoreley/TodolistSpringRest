package ik.koresh.TodolistSpringRest.services;



import ik.koresh.TodolistSpringRest.models.Status;
import ik.koresh.TodolistSpringRest.models.Task;
import ik.koresh.TodolistSpringRest.repositories.TasksRepository;
import ik.koresh.TodolistSpringRest.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public Optional<Task> findOne(String description){
        return tasksRepository.findByDescription(description);
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
        enrichTask(task);
        tasksRepository.save(task);
    }
    private void enrichTask(Task task){
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setCreatedWho("ADMIN");
    }

    @Transactional
    public void update(int id, Task updateTask){
        updateTask.setId(id);
        updateTask.setUpdatedAt(LocalDateTime.now());
        tasksRepository.save(updateTask);
    }

    @Transactional
    public void delete(int id){
        findOneRest(id);
        tasksRepository.deleteById(id);
    }
}
