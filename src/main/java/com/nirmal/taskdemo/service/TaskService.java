package com.nirmal.taskdemo.service;

import com.nirmal.taskdemo.entity.Task;
import com.nirmal.taskdemo.mapper.TaskMapper;
import com.nirmal.taskdemo.model.TaskDto;
import com.nirmal.taskdemo.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nirmal.taskdemo.utility.AppUtility.logObjectAsString;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public String createTask(TaskDto taskDto) {
        log.info("Task to create: {}", logObjectAsString(taskDto));
        Task task = taskRepository.save(taskMapper.mapTask(taskDto));
        log.info("Task created for title {}", taskDto.getTitle());
        return task.getId();
    }

    public List<TaskDto> getAllTasks() {
        log.info("Collecting all tasks");
        return taskMapper.mapTasks(taskRepository.findAll());
    }

    public TaskDto getTaskById(String taskId) {
        log.info("Finding task by ID {}", taskId);
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(taskMapper::map).orElse(null);
    }

    public boolean deleteTask(String taskId) {
        log.info("Deleting task by ID {}", taskId);
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            return false;
        }
        taskRepository.delete(task.get());
        log.info("Deleted task by ID {}", taskId);
        return true;
    }

    public TaskDto updateTask(TaskDto taskDto) {
        log.info("Updating task by ID {}", taskDto.getId());
        Optional<Task> optionalTask = taskRepository.findById(taskDto.getId());
        if (optionalTask.isEmpty()) {
            log.info("Requested task {} doesn't exist", taskDto.getId());
            return null;
        }

        Task task = optionalTask.get();
        taskMapper.updateTaskFromDto(taskDto, task);
        task = taskRepository.save(task);
        log.info("Updated task by ID {}", task.getId());
        return taskMapper.map(task);
    }
}
