package com.solovev.springrestsoap.dto.soap.task;

import com.solovev.springrestsoap.model.Task;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateTaskRequest", namespace = "http://localhost:8080/user")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateTaskRequest {

    @XmlElement(namespace = "http://localhost:8080/user")
    private Long userId;

    @XmlElement(required = true)
    private Task task;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}

