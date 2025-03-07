package com.solovev.springrestsoap.model;

import com.solovev.springrestsoap.service.adapter.LocalDateTimeAdapter;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.RepresentationModel;

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
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime createdDate;

    @XmlElement(name = "deadline")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime deadline;


}
