package com.example.hsqldb.servise;

import com.example.hsqldb.JobStatus;
import com.example.hsqldb.model.TaskDto;
import com.example.hsqldb.model.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<TaskEntity> findEntityById(int id) throws Exception;
    List<TaskEntity> findAll();
    TaskEntity save (TaskDto task);
    TaskEntity update(int id,TaskDto task);
    boolean delete (int id);
    TaskEntity changeStatus(int id, JobStatus status);
}
