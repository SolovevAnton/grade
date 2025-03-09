package com.solovev.client;

import com.solovev.springrestsoap.dto.soap.user.CreateUserRequest;
import com.solovev.springrestsoap.dto.soap.user.GetUserRequest;
import com.solovev.springrestsoap.model.User;
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

    public Object getUser(Long userId) {
        UserPort userPort = service.getPort(PORT_NAME, UserPort.class);
        GetUserRequest request = new GetUserRequest();
        request.setUserId(userId);

        var response = userPort.GetUser(request);
        return response;

    }

    public Object createUser(User user) {
        UserPort userPort = service.getPort(PORT_NAME, UserPort.class);
        CreateUserRequest request = new CreateUserRequest();
        request.setUser(user);

        var response = userPort.CreateUser(request);
        return response;
    }

    public static void main(String[] args) throws Exception {
        UserClient client = new UserClient();

        // Example: Get a user
        System.out.println(client.getUser(1L));

        // Example: Create a user
        User newUser = new User();
        newUser.setId(2L);
        newUser.setName("John");
        newUser.setSurname("Doe");
        newUser.setEmail("john.doe@example.com");

        System.out.println(client.createUser(newUser));
    }
}

