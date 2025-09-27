package com.nirmal.taskdemo.model;

import com.nirmal.taskdemo.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Jacksonized
public class TaskDto {

    private String id;
    @NotNull
    private String title;
    private String description;
    private Status status;
    private LocalDate createdTime;
    private LocalDate lastModifiedTime;
}
