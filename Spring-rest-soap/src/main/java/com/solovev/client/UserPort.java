package com.solovev.client;


import com.solovev.springrestsoap.dto.soap.task.CreateTaskRequest;
import com.solovev.springrestsoap.dto.soap.task.DeleteTaskRequest;
import com.solovev.springrestsoap.dto.soap.task.GetAllTasksRequest;
import com.solovev.springrestsoap.dto.soap.task.GetTaskRequest;
import com.solovev.springrestsoap.dto.soap.user.CreateUserRequest;
import com.solovev.springrestsoap.dto.soap.user.DeleteUserRequest;
import com.solovev.springrestsoap.dto.soap.user.GetUserRequest;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(name = "UserPort")
public interface UserPort {

    @WebMethod
    Object GetUser(@WebParam(name = "GetUserRequest") GetUserRequest request);

    @WebMethod
    Object CreateUser(@WebParam(name = "CreateUserRequest") CreateUserRequest request);

    @WebMethod
    void DeleteUser(@WebParam(name = "DeleteUserRequest") DeleteUserRequest request);

    @WebMethod
    Object GetTask(@WebParam(name = "GetTaskRequest") GetTaskRequest request);

    @WebMethod
    void CreateTask(@WebParam(name = "CreateTaskRequest") CreateTaskRequest request);

    @WebMethod
    void DeleteTask(@WebParam(name = "DeleteTaskRequest") DeleteTaskRequest request);

    @WebMethod
    void GetAllTasks(@WebParam(name = "GetAllTasksRequest") GetAllTasksRequest request);
}

