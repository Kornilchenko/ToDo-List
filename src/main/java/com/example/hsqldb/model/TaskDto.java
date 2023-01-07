package com.example.hsqldb.model;

import com.example.hsqldb.JobStatus;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class TaskDto {

    @NotBlank
    @NotNull(message = "{notnulmessage}")
    private String task;

    public TaskDto setTask(String task) {
        this.task = task;
        return this;
    }

    public String getTask() {
        return task;
    }

    public String getPlace() {
        return place;
    }

    public TaskDto setPlace(String place) {
        this.place = place;
        return this;
    }

    private String place;

    @NotNull(message = "{notnulmessage}")
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @Enumerated(EnumType.STRING)
    public JobStatus getStatus() {
        return status;
    }

    @Enumerated(EnumType.STRING)
    public TaskDto setStatus(JobStatus status) {
        this.status = status;
        return this;
    }

    public TaskDto() {
        //Do nothing
    }
}
