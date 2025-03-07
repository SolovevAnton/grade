package com.solovev.springrestsoap.dto.soap.task;

import com.solovev.springrestsoap.model.Task;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlRootElement(name = "GetTaskResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllTasksResponse {

    @XmlElementWrapper(name = "tasks")
    @XmlElement(required = true)
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

