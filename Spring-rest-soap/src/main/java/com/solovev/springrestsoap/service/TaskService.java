package com.solovev.springrestsoap.service;

import com.solovev.springrestsoap.model.Task;
import com.solovev.springrestsoap.model.User;
import com.solovev.springrestsoap.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
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

    @Transactional
    public Task save(Long userId, Task task) {
        userService.getById(userId).orElseThrow().addTask(task);
        var savedTask = taskRepository.save(task);
        return savedTask;
    }

    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Transactional
    public void deleteById(Long userId, Long taskId) {
        User user = userService.getById(userId).orElseThrow();
        Task task = taskRepository.findById(taskId).orElseThrow();
        boolean wasDeleted = user.getTasks().remove(task);
        if (!wasDeleted) {
            throw new NoSuchElementException("Task does not belong to this user");
        }
        taskRepository.delete(task);
    }

    private final TaskRepository taskRepository;
    private final UserService userService;
}
