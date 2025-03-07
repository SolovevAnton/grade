package com.solovev.springrestsoap.endpoint;

import com.solovev.springrestsoap.dto.soap.user.GetUserRequest;
import com.solovev.springrestsoap.dto.soap.user.GetUserResponse;
import com.solovev.springrestsoap.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @PayloadRoot(namespace = "http://localhost:8080/user", localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();

        userService.getById(request.getUserId()).ifPresentOrElse(
                response::setUser,
                () -> {
                    throw new RuntimeException("User not found");
                }
        );

        return response;
    }

}

