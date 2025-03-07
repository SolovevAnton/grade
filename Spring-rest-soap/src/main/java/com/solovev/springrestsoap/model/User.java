package com.solovev.springrestsoap.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity
@Data
@Table(name = "users")
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Task.class, fetch = FetchType.EAGER)
    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    private List<Task> tasks;

    public boolean addTask(Task task) {
        return tasks.add(task);
    }
}
