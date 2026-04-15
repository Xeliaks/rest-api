package com.example.tasks.unit;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.tasks.dto.TaskUpdateRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class TaskUpdateRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validRequest_hasNoViolations() {
        TaskUpdateRequest req = new TaskUpdateRequest();
        req.setTitle("Valid Title");
        req.setDescription("Updated description");
        req.setCompleted(true);

        Set<ConstraintViolation<TaskUpdateRequest>> violations = validator.validate(req);

        assertThat(violations).isEmpty();
    }

    @Test
    void nullTitle_failsValidation() {
        TaskUpdateRequest req = new TaskUpdateRequest();
        req.setTitle(null);
        req.setCompleted(true);

        Set<ConstraintViolation<TaskUpdateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title must not be blank");
    }

    @Test
    void blankTitle_failsValidation() {
        TaskUpdateRequest req = new TaskUpdateRequest();
        req.setTitle("   "); 
        req.setCompleted(false);

        Set<ConstraintViolation<TaskUpdateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title must not be blank");
    }

    @Test
    void shortTitle_failsValidation() {
        TaskUpdateRequest req = new TaskUpdateRequest();
        req.setTitle("No"); // Length 2 (minimum is 3)
        req.setCompleted(true);

        Set<ConstraintViolation<TaskUpdateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title length must be between 3 and 100 characters");
    }

    @Test
    void overlyLongTitle_failsValidation() {
        TaskUpdateRequest req = new TaskUpdateRequest();
        req.setTitle("A".repeat(101)); 
        req.setCompleted(false);

        Set<ConstraintViolation<TaskUpdateRequest>> violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("Title length must be between 3 and 100 characters");
    }
}