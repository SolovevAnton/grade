package com.solovev.soapclient;


import com.solovev.soapclient.generated.*;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceClient;

import javax.xml.namespace.QName;
import java.net.URL;

@WebServiceClient(name = "userClient",
        wsdlLocation = "src/main/resources/service.wsdl",
        targetNamespace = "http://localhost:8080/user")
public class UserClient {
    private static final String WSDL_URL = "http://localhost:8080/ws/users.wsdl";
    private static final QName SERVICE_NAME = new QName("http://localhost:8080/user", "UserPortService");
    private static final QName PORT_NAME = new QName("http://localhost:8080/user", "UserPortSoap11");

    private Service service;

    public UserClient() throws Exception {
        URL url = new URL(WSDL_URL);
        service = Service.create(url, SERVICE_NAME);
    }

    public GetUserResponse getUser(Long userId) {
        UserPort userPort = service.getPort(PORT_NAME, UserPort.class);
        GetUserRequest request = new GetUserRequest();
        request.setUserId(userId);

        return userPort.getUser(request);

    }

    public Object createUser(User user) {
        UserPort userPort = service.getPort(PORT_NAME, UserPort.class);
        CreateUserRequest request = new CreateUserRequest();
        request.setUser(user);

        return userPort.createUser(request);
    }

    public static void main(String[] args) throws Exception {
        UserClient client = new UserClient();
        GetUserResponse response = client.getUser(1L);
        // Example: Get a user
        System.out.println(response);

        System.out.println();
        // Example: Create a user
        User newUser = new User();
        newUser.setId(2L);
        newUser.setName("John");
        newUser.setSurname("Doe");
        newUser.setEmail("john.doe@example.com");
        newUser.setTasks(new User.Tasks());

        System.out.println(client.createUser(newUser));
    }
}

