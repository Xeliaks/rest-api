package com.example.tasks.unit;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.tasks.dto.TaskCreateRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class TaskCreateRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validRequest_hasNoViolations() {
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle("Valid Title"); 
        req.setDescription("Some description");

        Set<ConstraintViolation<TaskCreateRequest>> violations = validator.validate(req);

        assertThat(violations).isEmpty();
    }

    @Test
    void nullTitle_failsValidation() {
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle(null);

        Set<ConstraintViolation<TaskCreateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title must not be blank");
    }

    @Test
    void blankTitle_failsValidation() {
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle("   "); 

        Set<ConstraintViolation<TaskCreateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title must not be blank");
    }

    @Test
    void shortTitle_failsValidation() {
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle("Hi"); 

        Set<ConstraintViolation<TaskCreateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title length must be between 3 and 100 characters");
    }

    @Test
    void overlyLongTitle_failsValidation() {
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle("A".repeat(101)); 

        Set<ConstraintViolation<TaskCreateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title length must be between 3 and 100 characters");
    }
}