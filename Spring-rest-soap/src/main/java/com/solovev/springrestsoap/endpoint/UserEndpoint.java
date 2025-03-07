package com.solovev.springrestsoap.endpoint;

import com.solovev.springrestsoap.dto.soap.user.CreateUserRequest;
import com.solovev.springrestsoap.dto.soap.user.DeleteUserRequest;
import com.solovev.springrestsoap.dto.soap.user.GetUserRequest;
import com.solovev.springrestsoap.dto.soap.user.GetUserResponse;
import com.solovev.springrestsoap.model.User;
import com.solovev.springrestsoap.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.NoSuchElementException;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) throws NoSuchElementException {
        GetUserResponse response = new GetUserResponse();

        userService.getById(request.getUserId()).ifPresentOrElse(
                response::setUser,
                () -> {
                    throw new NoSuchElementException("No user with this id found");
                }
        );

        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "CreateUserRequest")
    @ResponsePayload
    public GetUserResponse createUser(@RequestPayload CreateUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(userService.save(request.getUser()));
        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "DeleteUserRequest")
    @ResponsePayload
    public GetUserResponse deleteUser(@RequestPayload DeleteUserRequest request) throws NoSuchElementException {
        GetUserResponse response = new GetUserResponse();
        User toDelete = userService.getById(request.getUserId()).orElseThrow();
        response.setUser(toDelete);
        userService.deleteById(request.getUserId());
        return response;
    }

}

