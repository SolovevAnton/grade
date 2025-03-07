package com.solovev.springrestsoap.dto.soap.task;

import com.solovev.springrestsoap.model.Task;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetTaskResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetTaskResponse {

    @XmlElement(required = true)
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}

