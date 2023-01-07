package com.example.hsqldb.model;

import com.example.hsqldb.JobStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class TaskEntity {
    public TaskEntity() {
        //Do nothing
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public TaskEntity setId(int id) {
        this.id = id;
        return this;
    }

    @NotNull
    @NotBlank
    private String task;

    public TaskEntity setTask(String what) {
        this.task = what;
        return this;
    }

    public String getTask() {
        return task;
    }

    public String getPlaceOfExecution() {
        return placeOfExecution;
    }

    public TaskEntity setPlaceOfExecution(String place) {
        this.placeOfExecution = place;
        return this;
    }

    @Column(name="place", length=150, nullable=false)
    private String placeOfExecution;


    @Enumerated(EnumType.STRING)
    public JobStatus getExecutionStatus() {
        return executionStatus;
    }

    @Enumerated(EnumType.STRING)
    public TaskEntity setExecutionStatus(JobStatus executionStatus) {
        this.executionStatus = executionStatus;
        return this;
    }

    @Column(name="status", length=20, nullable=false)
    @Enumerated(EnumType.STRING)
    private JobStatus executionStatus;

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public TaskEntity setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
        return this;
    }

    @Basic
    @Column(name="time_of_creation", nullable=false)
    private LocalDateTime timeOfCreation;

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public TaskEntity setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
        return this;
    }

    @Basic
    @Column(name="completion_time")
    private LocalDateTime completionTime;

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null ||getClass() != object.getClass()) return false;
        return this.id ==((TaskEntity) object).id;
    }

    @Override
    public int hashCode(){
        int result = id;
        result = 18 * result + (task != null ? task.hashCode() : 0);
        result = 18 * result + (placeOfExecution != null ? placeOfExecution.hashCode() : 0);
        result = 18 * result + (executionStatus!= null ? executionStatus.hashCode() : 0);
        result = 18 * result + (timeOfCreation != null ? timeOfCreation.hashCode() : 0);
        result = 18 * result + (completionTime != null ? completionTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", place='" + placeOfExecution + '\'' +
                ", status='" + executionStatus + '\'' +
                '}';
    }
}
