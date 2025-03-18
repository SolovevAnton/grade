package com.solovev.springrestsoap.endpoint;

import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.CreateTaskRequest;
import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.DeleteTaskRequest;
import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.GetTaskRequest;
import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.GetTaskResponse;
import com.solovev.springrestsoap.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "GetTaskRequest")
    @ResponsePayload
    public GetTaskResponse getTask(@RequestPayload GetTaskRequest request) throws NoSuchElementException {
        GetTaskResponse response = new GetTaskResponse();

        taskService.findById(request.getTaskId()).ifPresentOrElse(
                t -> response.setTask(taskMapper.toDto(t)),
                () -> {
                    throw new NoSuchElementException("No task with this id found");
                }
        );

        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "CreateTaskRequest")
    @ResponsePayload
    public GetTaskResponse createTask(@RequestPayload CreateTaskRequest request) {
        GetTaskResponse response = new GetTaskResponse();
        var savedTask = taskService.save(request.getUserId(), taskMapper.toModel(request.getTask()));
        response.setTask(taskMapper.toDto(savedTask));
        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "DeleteTaskRequest")
    @ResponsePayload
    public GetTaskResponse deleteTask(@RequestPayload DeleteTaskRequest request) throws NoSuchElementException {
        GetTaskResponse response = new GetTaskResponse();
        var toDelete = taskService.findById(request.getTaskId()).orElseThrow();
        response.setTask(taskMapper.toDto(toDelete));
        taskService.deleteById(request.getUserId(), request.getTaskId());
        return response;
    }
}
