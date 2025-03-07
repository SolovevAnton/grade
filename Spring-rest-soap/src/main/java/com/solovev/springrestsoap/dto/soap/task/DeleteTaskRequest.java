package com.solovev.springrestsoap.dto.soap.task;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DeleteTaskRequest", namespace = "http://localhost:8080/user")
public class DeleteTaskRequest {
    @XmlElement(namespace = "http://localhost:8080/user")
    private Long userId;

    @XmlElement(namespace = "http://localhost:8080/user")
    protected Long taskId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long value) {
        this.taskId = value;
    }
}

