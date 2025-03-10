package com.solovev.soapclient.dto.soap.user;

import com.solovev.soapclient.model.User;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateUserRequest", namespace = "http://localhost:8080/user")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateUserRequest {

    @XmlElement(required = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

