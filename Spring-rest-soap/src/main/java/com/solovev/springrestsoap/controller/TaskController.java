package com.solovev.springrestsoap.controller;

import com.solovev.springrestsoap.controller.hateoas.TaskLinksProvider;
import com.solovev.springrestsoap.model.Task;
import com.solovev.springrestsoap.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.solovev.springrestsoap.config.APIEndpoints.USERS_REST;

@RestController
@RequestMapping(USERS_REST)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskLinksProvider taskLinksProvider;

    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable("userId") Long userId, @PathVariable("taskId") Long taskId) {
        Task found = taskService.findById(taskId).orElseThrow();
        taskLinksProvider.addLinks(found, userId);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable("userId") Long userId) {
        var tasks = taskService.findByUserId(userId);
        tasks.forEach(task -> taskLinksProvider.addLinks(task, userId));
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{userId}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable Long userId, @RequestBody Task task) {
        Task created = taskService.save(userId, task);
        Link self = taskLinksProvider.addLinks(created, userId);
        return ResponseEntity.created(self.toUri()).body(created);
    }

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long userId, @PathVariable("taskId") Long taskId) {
        taskService.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }

}
