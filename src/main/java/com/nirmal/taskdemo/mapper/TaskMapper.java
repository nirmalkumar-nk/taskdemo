package com.nirmal.taskdemo.mapper;

import com.nirmal.taskdemo.entity.Task;
import com.nirmal.taskdemo.model.TaskDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper
public interface TaskMapper {

    Task mapTask(TaskDto taskDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDto(TaskDto taskDto, @MappingTarget Task task);

    TaskDto map(Task task);

    List<TaskDto> mapTasks(List<Task> tasks);
}
