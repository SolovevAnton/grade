package com.solovev.soapclient.dto.soap.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DeleteUserRequest", namespace = "http://localhost:8080/user")
public class DeleteUserRequest {
    @XmlElement(namespace = "http://localhost:8080/user")
    protected Long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long value) {
        this.userId = value;
    }
}

