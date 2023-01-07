package com.example.hsqldb;


import com.example.hsqldb.model.TaskDto;
import com.example.hsqldb.model.TaskEntity;
import com.example.hsqldb.repository.TaskRepository;
import com.example.hsqldb.servise.TaskServiceImplementation;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class) // register the Mockito extension
class CustomerRepositoryTest {
    private final int id=1;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    TaskServiceImplementation service;

    TaskDto dto = new TaskDto();
    TaskEntity entity = new TaskEntity();
    TaskEntity returnEntity = new TaskEntity();
    @BeforeEach
    void setDtoAndEntity(){
        dto.setTask("task");
        dto.setPlace("place");
        dto.setStatus(JobStatus.PLANNED);
        entity.setId(id);
        entity.setTask("task");
        entity.setPlaceOfExecution("place");
        entity.setExecutionStatus(JobStatus.PLANNED);
        entity.setTimeOfCreation(LocalDateTime.parse("2022-05-08T12:35:29"));
        returnEntity.setId(id);
        returnEntity.setTask("task");
        returnEntity.setPlaceOfExecution("place");
        returnEntity.setExecutionStatus(JobStatus.PLANNED);
        returnEntity.setTimeOfCreation(LocalDateTime.parse("2022-08-08T12:35:29"));
    }

    @Test
    void updateStatusPlannedInDone() {
        when(service.findEntityById(id)).thenReturn(Optional.of(entity));
        dto.setStatus(JobStatus.DONE);
        assertNull(service.update(id, dto));
    }

    @Test
    void changeStatusPlannedInCancelled() {
        when(service.findEntityById(id)).thenReturn(Optional.of(entity));
        TaskEntity task = service.changeStatus(id, JobStatus.CANCELLED);
        assertThat(JobStatus.CANCELLED, Is.is(task.getExecutionStatus()));
    }

    @Test
    void changeStatusPlannedInWork()  {
        when(service.findEntityById(id)).thenReturn(Optional.of(entity));
        TaskEntity task = service.changeStatus(id, JobStatus.WORK_IN_PROGRESS);
        assertThat(JobStatus.WORK_IN_PROGRESS, Is.is(task.getExecutionStatus()));
    }

    @Test
    void changeStatusWorkInCancelled()  {
        entity.setExecutionStatus(JobStatus.WORK_IN_PROGRESS);
        when(service.findEntityById(id)).thenReturn(Optional.of(entity));
        assertNull(service.changeStatus(id, JobStatus.CANCELLED));
    }

    @Test
    void changeStatusWorkInPlanned()  {
        entity.setExecutionStatus(JobStatus.WORK_IN_PROGRESS);
        when(service.findEntityById(id)).thenReturn(Optional.of(entity));
        assertNull(service.changeStatus(id, JobStatus.PLANNED));
    }

    @Test
    void changeStatusWorkInDone() {
        entity.setExecutionStatus(JobStatus.WORK_IN_PROGRESS);
        when(service.findEntityById(id)).thenReturn(Optional.of(entity));
        TaskEntity task = service.changeStatus(id, JobStatus.DONE);
        assertThat(JobStatus.DONE, Is.is(task.getExecutionStatus()));
    }

    @Test
    void changeStatusDoneInPlanned(){
        entity.setExecutionStatus(JobStatus.DONE);
        when(service.findEntityById(id)).thenReturn(Optional.of(entity));
        assertNull(service.changeStatus(id, JobStatus.PLANNED));
    }


   /* @Test
    void jsonAssertExample() throws JSONException {
        String result = "{\"name\": \"duke\", \"age\":\"42\"}";
        JSONAssert.assertEquals("{\"name\": \"duke\"}", result, false);
    }

    @Test
    void jsonPathExample() {
        String result = "{\"age\":\"42\", \"name\": \"duke\", \"tags\":[\"java\", \"jdk\"]}";
        //В качестве первого примера давайте проверим длину массива и значение атрибута:
        // Using JUnit 5 Assertions
        assertEquals(2, JsonPath.parse(result).read("$.tags.length()", Long.class));
        assertEquals("duke", JsonPath.parse(result).read("$.name", String.class));
    }*/
}
