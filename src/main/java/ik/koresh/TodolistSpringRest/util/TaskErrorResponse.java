package ik.koresh.TodolistSpringRest.util;


import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class TaskErrorResponse {
    private String message;
    private Date dateTime;


    public TaskErrorResponse(String message, Date dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }
}
