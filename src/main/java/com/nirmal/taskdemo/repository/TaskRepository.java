package com.nirmal.taskdemo.repository;

import com.nirmal.taskdemo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findAll();
}
