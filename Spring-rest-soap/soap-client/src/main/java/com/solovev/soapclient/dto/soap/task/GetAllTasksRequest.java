package com.solovev.soapclient.dto.soap.task;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetAllTasksRequest", namespace = "http://localhost:8080/user")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllTasksRequest {

    @XmlElement(namespace = "http://localhost:8080/user")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
