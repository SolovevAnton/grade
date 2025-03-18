package com.solovev.springrestsoap.endpoint;

import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.CreateUserRequest;
import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.DeleteUserRequest;
import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.GetUserRequest;
import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.GetUserResponse;
import com.solovev.springrestsoap.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) throws NoSuchElementException {
        GetUserResponse response = new GetUserResponse();

        userService.getById(request.getUserId()).ifPresentOrElse(
                u -> response.setUser(userMapper.toDto(u)),
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
        User in = userMapper.toModel(request.getUser());
        var out = userMapper.toDto(userService.save(in));
        response.setUser(out);
        return response;
    }

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "DeleteUserRequest")
    @ResponsePayload
    public GetUserResponse deleteUser(@RequestPayload DeleteUserRequest request) throws NoSuchElementException {
        GetUserResponse response = new GetUserResponse();
        User toDelete = userService.getById(request.getUserId()).orElseThrow();
        response.setUser(userMapper.toDto(toDelete));
        userService.deleteById(request.getUserId());
        return response;
    }

}

