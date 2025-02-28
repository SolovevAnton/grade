package com.solovev.springrestsoap.controller;

import com.solovev.springrestsoap.model.Task;
import com.solovev.springrestsoap.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.solovev.springrestsoap.config.APIEndpoints.USERS_REST;

@RestController
@RequestMapping(USERS_REST)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable("userId") Long userId, @PathVariable("taskId") Long taskId) {
        return ResponseEntity.of(taskService.findById(taskId));
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getTask(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(taskService.findByUserId(userId));
    }

    @PostMapping("/{userId}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable Long userId, @RequestBody Task task) {
        return ResponseEntity.status(201).body(taskService.save(userId, task));
    }

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long userId, @PathVariable("taskId") Long taskId) {
        taskService.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }

}
