package com.nirmal.taskdemo.controller;

import com.nirmal.taskdemo.model.TaskDto;
import com.nirmal.taskdemo.service.TaskService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(path = "/task")
    public HttpEntity<String> createTask(@NotNull @RequestBody TaskDto taskDto) {
        log.info("Call to create Task");
        String taskId = taskService.createTask(taskDto);
        return new ResponseEntity<>(taskId, HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public List<TaskDto> getTasks() {
        log.info("Call to list all tasks");
        return taskService.getAllTasks();
    }

    @GetMapping(path = "/task/{task_id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("task_id") String taskId) {
        log.info("Call to get task {}", taskId);
        TaskDto taskDto = taskService.getTaskById(taskId);
        if (isNull(taskDto)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/task/{task_id}")
    public HttpStatus deleteTask(@PathVariable("task_id") String taskId) {
        log.info("Call to delete task {}", taskId);
        boolean deleteFlag = taskService.deleteTask(taskId);
        return deleteFlag ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @PutMapping(path = "/task")
    public HttpEntity<TaskDto> updateTask(@NotNull @RequestBody TaskDto taskDto) {
        log.info("Call to update task {}", taskDto.getId());
        TaskDto updatedTask = taskService.updateTask(taskDto);
        if (isNull(updatedTask)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
}
