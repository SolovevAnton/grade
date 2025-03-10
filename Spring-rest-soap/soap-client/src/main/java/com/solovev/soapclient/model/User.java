package com.solovev.soapclient.model;

import jakarta.xml.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {


    private Long id;

    private String name;
    private String surname;
    private String email;

    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    private List<Task> tasks;

    public boolean addTask(Task task) {
        return tasks.add(task);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name,
                user.name) && Objects.equals(surname, user.surname) && Objects.equals(email,
                user.email) && Objects.equals(tasks, user.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, tasks);
    }
}
