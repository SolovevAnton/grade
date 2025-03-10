package com.solovev.soapclient.dto.soap.task;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetTaskRequest", namespace = "http://localhost:8080/user")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetTaskRequest {

    @XmlElement(namespace = "http://localhost:8080/user")
    private Long taskId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
