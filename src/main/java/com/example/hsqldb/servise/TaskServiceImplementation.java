package com.example.hsqldb.servise;

import com.example.hsqldb.JobStatus;
import com.example.hsqldb.model.TaskDto;
import com.example.hsqldb.model.TaskEntity;
import com.example.hsqldb.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TaskServiceImplementation implements TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskServiceImplementation.class);
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TaskEntity> findEntityById(int id) {
        return taskRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public ArrayList<TaskEntity> findAll() {
        return (ArrayList<TaskEntity>) taskRepository.findAll();
    }


    @Override
    public TaskEntity save(TaskDto task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTask(task.getTask());
        taskEntity.setPlaceOfExecution(task.getPlace());
        taskEntity.setExecutionStatus(task.getStatus());
        taskEntity.setTimeOfCreation(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return taskRepository.save(taskEntity);
    }


    @Transactional
    @Override
    public TaskEntity update(int id, TaskDto dto) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            log.error("Not found object id = {}", id);
            return null;
        }
        if (!checksTheExecutionSequence(dto, entity)) {
            log.info("status no valid, last status {}, future status {}", entity.getExecutionStatus(), dto.getStatus());
            return null;
        }
        entity.setId(id)
                .setTask(dto.getTask())
                .setPlaceOfExecution(dto.getPlace())
                .setExecutionStatus(dto.getStatus());
        taskRepository.save(entity);
        return entity;
    }

    @Override
    public boolean delete(int id) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null)
            return false;
        taskRepository.deleteById(id);
        return true;
    }

    @Transactional
    @Override
    public TaskEntity changeStatus(int id, JobStatus status) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            log.error("Not found object id = {}", id);
            return null;
        }
        TaskDto dto = new TaskDto()
                .setTask(entity.getTask())
                .setPlace(entity.getPlaceOfExecution())
                .setStatus(status);
        return update(id, dto);
    }

    private boolean checksTheExecutionSequence(TaskDto future, TaskEntity last) {
        log.info("run method checks The Execution Sequence");
        if (future.getStatus() == last.getExecutionStatus()) {
            log.info("same statuses {}", last.getExecutionStatus());
            return false;
        }
        switch (last.getExecutionStatus()) {
            case PLANNED:
                if (future.getStatus() == JobStatus.DONE)
                    return false;
                break;
            case WORK_IN_PROGRESS:
                if (future.getStatus() == JobStatus.DONE) {
                    last.setCompletionTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    return true;
                }
                if (future.getStatus() == JobStatus.CANCELLED || future.getStatus() == JobStatus.PLANNED)
                    return false;
                break;
            case DONE:
            case CANCELLED:
                return false;
            default:
                break;
        }
        return true;
    }
}