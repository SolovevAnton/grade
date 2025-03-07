package com.solovev.springrestsoap.endpoint;

import com.solovev.springrestsoap.dto.soap.task.*;
import com.solovev.springrestsoap.model.Task;
import com.solovev.springrestsoap.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.NoSuchElementException;

@Endpoint
@RequiredArgsConstructor
public class TaskEndpoint {
    private final TaskService taskService;

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "GetTaskRequest")
    @ResponsePayload
    public GetTaskResponse getTask(@RequestPayload GetTaskRequest request) throws NoSuchElementException {
        GetTaskResponse response = new GetTaskResponse();

        taskService.findById(request.getTaskId()).ifPresentOrElse(
                response::setTask,
                () -> {
                    throw new NoSuchElementException("No task with this id found");
                }
        );

        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "GetAllTasksRequest")
    @ResponsePayload
    public GetAllTasksResponse getTask(@RequestPayload GetAllTasksRequest request) throws NoSuchElementException {
        GetAllTasksResponse response = new GetAllTasksResponse();

        response.setTasks(taskService.findByUserId(request.getUserId()));

        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "CreateTaskRequest")
    @ResponsePayload
    public GetTaskResponse createTask(@RequestPayload CreateTaskRequest request) {
        GetTaskResponse response = new GetTaskResponse();
        response.setTask(taskService.save(request.getUserId(), request.getTask()));
        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "DeleteTaskRequest")
    @ResponsePayload
    public GetTaskResponse deleteTask(@RequestPayload DeleteTaskRequest request) throws NoSuchElementException {
        GetTaskResponse response = new GetTaskResponse();
        Task toDelete = taskService.findById(request.getTaskId()).orElseThrow();
        response.setTask(toDelete);
        taskService.deleteById(request.getUserId(), request.getTaskId());
        return response;
    }
}
