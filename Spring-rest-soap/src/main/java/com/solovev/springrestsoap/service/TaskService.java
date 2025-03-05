package com.solovev.springrestsoap.service;

import com.solovev.springrestsoap.model.Task;
import com.solovev.springrestsoap.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    public List<Task> findByUserId(long userId) {
        return userService
                .getById(userId)
                .orElseThrow()
                .getTasks();
    }

    public Task save(Long userId, Task task) {
        userService.getById(userId).orElseThrow().addTask(task);
        var savedTask = taskRepository.save(task);
        return savedTask;
    }

    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private final TaskRepository taskRepository;
    private final UserService userService;
}
