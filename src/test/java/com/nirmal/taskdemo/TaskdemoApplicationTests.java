package com.nirmal.taskdemo;

import com.nirmal.taskdemo.entity.enums.Status;
import com.nirmal.taskdemo.model.TaskDto;
import com.nirmal.taskdemo.repository.TaskRepository;
import com.nirmal.taskdemo.service.TaskService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@Disabled
@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@ExtendWith(MockitoExtension.class)
class TaskdemoApplicationTests {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void contextLoads() {
        TaskDto taskDto = TaskDto.builder()
                .id("TASK_ID")
                .title("Task 1")
                .description("Task Description")
                .status(Status.IN_PROGRESS)
                .createdTime(LocalDate.now())
                .lastModifiedTime(LocalDate.now())
                .build();
    }

}
