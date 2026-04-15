package com.example.tasks.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.tasks.dto.TaskCreateRequest;
import com.example.tasks.service.TaskService;

@Component
public class TaskDataLoader implements CommandLineRunner {

    private final TaskService taskService;

    public TaskDataLoader(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!taskService.findAll(null).isEmpty()) {
            return;
        }

        System.out.println("Loading dummy tasks into in-memory store...");

        TaskCreateRequest task1 = new TaskCreateRequest();
        task1.setTitle("Buy groceries");
        task1.setDescription("Milk, eggs, and bread.");
        task1.setCompleted(false);
        taskService.create(task1);

        TaskCreateRequest task2 = new TaskCreateRequest();
        task2.setTitle("Learn Spring Boot");
        task2.setDescription("Finish the REST API project.");
        task2.setCompleted(true);
        taskService.create(task2);

        TaskCreateRequest task3 = new TaskCreateRequest();
        task3.setTitle("Call Mom");
        task3.setCompleted(false);
        taskService.create(task3);

        TaskCreateRequest task4 = new TaskCreateRequest();
        task4.setTitle("Pay electricity bill");
        task4.setCompleted(true);
        taskService.create(task4);

        TaskCreateRequest task5 = new TaskCreateRequest();
        task5.setTitle("Read Java book");
        task5.setDescription("Read chapter 4 and complete the practice exercises at the end.");
        task5.setCompleted(false);
        taskService.create(task5);

        TaskCreateRequest task6 = new TaskCreateRequest();
        task6.setTitle("Schedule dentist appointment");
        task6.setDescription("Ask for a morning slot next week.");
        task6.setCompleted(false);
        taskService.create(task6);

        System.out.println("6 Dummy tasks loaded successfully!");
    }
}