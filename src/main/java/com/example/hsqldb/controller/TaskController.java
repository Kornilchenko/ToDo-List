package com.example.hsqldb.controller;

import com.example.hsqldb.JobStatus;
import com.example.hsqldb.servise.TaskService;
import com.example.hsqldb.model.TaskDto;
import com.example.hsqldb.model.TaskEntity;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.net.Proxy.Type.HTTP;


@Validated
@Tag(name = "My task page", description = "ver.1")
@OpenAPIDefinition()
@RestController
//@PreAuthorize("isAuthenticated()")
@RequestMapping("/task")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        log.info("task service was be autowired");
        this.taskService = taskService;
    }

    //@PreAuthorize("permitAll()") //доступно всем
    @Operation(summary = "повернення всіх завдань")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "все працює.")})
    @GetMapping("/all")
    public ResponseEntity<Iterable<TaskEntity>> getTasksAll() {
        log.info("get all tasks");
        return ResponseEntity.ok(taskService.findAll());
    }

    //@PreAuthorize("isAuthenticated()")
    // @PreAuthorize("hasRole('USER')")
    @Operation(summary = "повернення завданя по ip")
    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> findById(@Parameter(description = "Введіть ID завдання")
                                               @PathVariable int id) throws Exception {
        log.info("return task by id");
        return taskService.findEntityById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "видалення завданя по ip")
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskEntity> deleteTask(@PathVariable final int id) {
        if (!taskService.delete(id)) {
            log.info("Deletion not possible, no id {}", id);
            return ResponseEntity.badRequest().build();
        }
        log.info("Delete task id = {}", id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "оновлення завданя")
    @PutMapping("/change/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable int id, @RequestBody @Validated TaskDto task,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            log.info("method put error validated");
            result.getAllErrors().forEach(error -> {
                log.info("error file name {}", ((FieldError) error).getField());
                log.info("error massage {}", error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().build();
        } else {
            log.info("run method put");
            TaskEntity taskEntity = taskService.update(id, task);
            if (taskEntity == null)
                return ResponseEntity.badRequest().build();
            return ResponseEntity.ok().body(taskEntity);
        }
    }

    @Operation(summary = "зміна статусу завдання")
    @PutMapping("/change_status/{id}")
    public ResponseEntity<TaskEntity> changeStatus(@PathVariable int id, JobStatus status) {
        log.info("run method change status");
        TaskEntity task = taskService.changeStatus(id, status);
        if (task == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(task);

    }

    @Operation(summary = "додавання завданя")
    @PostMapping("/new")
    public ResponseEntity<TaskEntity> postTask(@RequestBody @Validated TaskDto task, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                log.info("error file name {}", ((FieldError) error).getField());
                log.info("error massage {}", error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().build();
        } else {
            log.info("run method post");
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
        }
    }
}
