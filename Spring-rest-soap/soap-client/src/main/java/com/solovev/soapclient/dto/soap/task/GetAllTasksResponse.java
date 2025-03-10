package com.solovev.soapclient.dto.soap.task;

import com.solovev.soapclient.model.Task;
import jakarta.xml.bind.annotation.*;

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

