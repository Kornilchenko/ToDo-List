package com.example.hsqldb;

import com.example.hsqldb.controller.TaskController;
import com.example.hsqldb.model.TaskDto;
import com.example.hsqldb.model.TaskEntity;
import com.example.hsqldb.servise.TaskServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskServiceImplementation service;

    @Autowired
    ObjectMapper mapper;

    private final int id = 1;
    TaskDto dto = new TaskDto();
    TaskEntity entity = new TaskEntity();
    TaskEntity returnEntity = new TaskEntity();

    @BeforeEach
    void setDtoAndEntity() {
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
        returnEntity.setPlaceOfExecution("place2");
        returnEntity.setExecutionStatus(JobStatus.PLANNED);
        returnEntity.setTimeOfCreation(LocalDateTime.parse("2022-08-08T12:35:29"));
    }

    @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testAuth() throws Exception {
        when(service.findEntityById(id)).thenReturn(Optional.of(returnEntity));
        mockMvc.perform(
                        get("/task/1"))
                .andExpect(status().isOk());
    }


    @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void searchByIdTest() throws Exception {
        when(service.findEntityById(id)).thenReturn(Optional.of(returnEntity));
        mockMvc.perform(
                        get("/task/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.executionStatus", Is.is("PLANNED")));
    }

    @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void createTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/task/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(dto));
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void saveOrUpdate() throws Exception {
        Mockito.when(service.update(id, dto)).thenReturn(null);
        //Mockito.when(service.findOne(updatedRecord.getId())).thenReturn(null);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/task/change/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(entity));
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
        //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void updateTest() throws Exception {
        when(service.save(dto)).thenReturn(entity);
        //Mockito.when(service.findOne(updatedRecord.getId())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/task/change/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void findAll() throws Exception {
        List<TaskEntity> tasks = Arrays.asList(
                entity, returnEntity
        );
        when(service.findAll()).thenReturn((ArrayList<TaskEntity>) tasks);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].executionStatus", Is.is("PLANNED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].executionStatus", Is.is("PLANNED")));
    }

    @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void changePlaceTest() throws Exception {
        when(service.update(id, dto)).thenReturn(returnEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/task/change/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.placeOfExecution", Is.is("place2")));
    }



    // TODO fix its
   /* @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void patch() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(Status.WORK_IN_PROGRESS));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }*/

   /* @Test
    public void shouldReturnTheTaskById() throws Exception {
        TaskEntity entity  = new TaskEntity();
        when(taskService.findEntityById(1)).thenReturn(Optional.of(entity));

        this.mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.task", Matchers.is("drink coffee")))
                .andExpect(jsonPath("$.place", Matchers.is("home")))
                .andExpect(jsonPath("$.status", Matchers.is("DONE")))
                .andExpect(jsonPath("$.time_of_creation", Matchers.is("2022-05-08 12:35:29")))
                .andExpect(jsonPath("$.completion_time", Matchers.is(null)));
    }*/

}
