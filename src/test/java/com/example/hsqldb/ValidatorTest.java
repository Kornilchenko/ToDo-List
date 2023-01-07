package com.example.hsqldb;


import com.example.hsqldb.model.TaskDto;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;


public class ValidatorTest {
    public static Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void taskIsEmpty() {
        TaskDto dto = new TaskDto();
        dto.setTask("");
        dto.setStatus(JobStatus.PLANNED);
        Set<ConstraintViolation<TaskDto>> constraintViolations = getValidator().validate(dto);
        assertEquals(1, constraintViolations.size());
        //assertEquals("не должно быть пустым", constraintViolations.iterator().next().getMessage());
        assertEquals("не може бути пустим", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void taskIsNull() {
        TaskDto dto = new TaskDto();
        dto.setTask(null);
        dto.setStatus(JobStatus.PLANNED);
        Set<ConstraintViolation<TaskDto>> constraintViolations = getValidator().validate(dto);
        assertEquals(2, constraintViolations.size());
        //assertEquals("не должно быть пустым", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void statusIsEmpty() {
        TaskDto dto = new TaskDto();
        Exception exception = assertThrows(RuntimeException.class, () -> dto.setStatus(JobStatus.valueOf("")));
        assertTrue(exception.getMessage().contains("No enum constant com.example.hsqldb.JobStatus."));
    }

    @Test
    void statusIsNull() {
        TaskDto dto = new TaskDto();
        dto.setTask("task");
        dto.setStatus(null);
        Set<ConstraintViolation<TaskDto>> constraintViolations = getValidator().validate(dto);
        assertEquals(1, constraintViolations.size());
        assertEquals("{notnulmessage}", constraintViolations.iterator().next().getMessage());
    }

}
