package com.solovev.soapclient.dto.soap.user;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "GetUserRequest", namespace = "http://localhost:8080/user")
@XmlType(name = "GetUserRequest", namespace = "http://localhost:8080/user")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetUserRequest {

    @XmlElement(name = "userId", required = true, namespace = "http://localhost:8080/user")
    protected Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}



