package com.nirmal.taskdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nirmal.taskdemo.TestUtility;
import com.nirmal.taskdemo.entity.Task;
import com.nirmal.taskdemo.entity.enums.Status;
import com.nirmal.taskdemo.mapper.TaskMapper;
import com.nirmal.taskdemo.model.TaskDto;
import com.nirmal.taskdemo.repository.TaskRepository;
import com.nirmal.taskdemo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@MockitoBeans({
        @MockitoBean(types = {TaskRepository.class})
})
public class TaskMockMvcTest {

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    private static TaskDto taskDto;
    private static final ObjectMapper objectMapper = TestUtility.objectMapper;
    private final static TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    private static final String TASK_ID_PATH = "/task/task_id";
    private static final String TASK_PATH = "/task";
    private static final String TASKS_PATH = "/tasks";

    @BeforeEach
    public void setUp() {
        taskDto = TaskDto.builder()
                .id("TASK_ID")
                .title("Task 1")
                .description("Task Description")
                .status(Status.IN_PROGRESS)
                .createdTime(LocalDate.now())
                .lastModifiedTime(LocalDate.now())
                .build();
    }

    @Test
    public void testCreate() throws Exception {
        when(taskService.createTask(any())).thenReturn("CREATED");

        mockMvc.perform(post(TASK_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("CREATED"));
    }

    @Test
    public void testGetAll() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(taskDto));

        mockMvc.perform(get(TASKS_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(Collections.singletonList(taskDto))));
    }

    @Test
    public void testGetTaskHappyPath() throws Exception {
        when(taskService.getTaskById(any())).thenReturn(taskDto);

        mockMvc.perform(get(TASK_ID_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(taskDto)));
    }

    @Test
    public void testGetTaskWhenTaskDoesNotExist() throws Exception {
        when(taskService.getTaskById(any())).thenReturn(null);

        mockMvc.perform(get(TASK_ID_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateTaskHappyPath() throws Exception {
        taskDto.setDescription("Task Updated");
        when(taskService.updateTask(any())).thenReturn(taskDto);

        mockMvc.perform(put(TASK_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(taskDto)));
    }

    @Test
    public void testDeleteTaskHappyPath() throws Exception {
        when(taskService.deleteTask(any())).thenReturn(true);

        mockMvc.perform(delete(TASK_ID_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTaskDoesNotExist() throws Exception {
        when(taskService.deleteTask(any())).thenReturn(false);

        mockMvc.perform(get(TASK_ID_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testMapper() throws JsonProcessingException {
        taskDto.setCreatedTime(null);

        Task task = Task.builder()
                .id("ID")
                .title("Title")
                .description("Description")
                .createdTime(LocalDate.now())
                .build();

        taskMapper.updateTaskFromDto(taskDto, task);
        System.out.println(objectMapper.writeValueAsString(task));
    }
}
