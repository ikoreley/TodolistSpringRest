package ik.koresh.TodolistSpringRest.repositories;


import ik.koresh.TodolistSpringRest.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT p from Task p")
    List<Task> myCustomFindAll();

    Optional<Task> findByDescription(String description);
}
