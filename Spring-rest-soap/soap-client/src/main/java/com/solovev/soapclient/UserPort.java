package com.solovev.soapclient;


import com.solovev.soapclient.dto.soap.task.CreateTaskRequest;
import com.solovev.soapclient.dto.soap.task.DeleteTaskRequest;
import com.solovev.soapclient.dto.soap.task.GetAllTasksRequest;
import com.solovev.soapclient.dto.soap.task.GetTaskRequest;
import com.solovev.soapclient.dto.soap.user.CreateUserRequest;
import com.solovev.soapclient.dto.soap.user.DeleteUserRequest;
import com.solovev.soapclient.dto.soap.user.GetUserRequest;
import com.solovev.soapclient.dto.soap.user.GetUserResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(name = "UserPort", targetNamespace = "http://localhost:8080/user")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface UserPort {

    @WebMethod
    GetUserResponse GetUser(
            @WebParam(name = "GetUserRequest", targetNamespace = "http://localhost:8080/user") GetUserRequest request);

    @WebMethod
    GetUserResponse CreateUser(@WebParam(name = "CreateUserRequest") CreateUserRequest request);

    @WebMethod
    void DeleteUser(@WebParam(name = "DeleteUserRequest") DeleteUserRequest request);

    @WebMethod
    GetUserResponse GetTask(@WebParam(name = "GetTaskRequest") GetTaskRequest request);

    @WebMethod
    void CreateTask(@WebParam(name = "CreateTaskRequest") CreateTaskRequest request);

    @WebMethod
    void DeleteTask(@WebParam(name = "DeleteTaskRequest") DeleteTaskRequest request);

    @WebMethod
    void GetAllTasks(@WebParam(name = "GetAllTasksRequest") GetAllTasksRequest request);
}

