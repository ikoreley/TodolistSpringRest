package ik.koresh.TodolistSpringRest.exception;

public class TaskNotCreatedException extends RuntimeException{
    public TaskNotCreatedException(String msg){
        super(msg);
    }
}
