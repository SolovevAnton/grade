package com.solovev.springrestsoap.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tasks")
@XmlRootElement(name = "Task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task extends RepresentationModel<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @XmlElement(name = "createdDate")
    @CreatedDate
    private LocalDateTime createdDate;

    @XmlElement(name = "deadline")
    private LocalDateTime deadline;

}
