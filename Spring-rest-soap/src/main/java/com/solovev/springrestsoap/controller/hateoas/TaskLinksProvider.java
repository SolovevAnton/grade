package com.solovev.springrestsoap.controller.hateoas;

import com.solovev.springrestsoap.controller.TaskController;
import com.solovev.springrestsoap.model.Task;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TaskLinksProvider {

    public Link addLinks(Task task, long userId) {
        Link self = addSelfLink(task, userId);
        addDeleteLink(task, userId);
        return self;
    }

    private Link addSelfLink(Task task, long userId) {
        Link selfLink = linkTo(methodOn(TaskController.class).getTask(userId, task.getId())).withSelfRel();
        task.add(selfLink);
        return selfLink;
    }

    private void addDeleteLink(Task task, long userId) {
        Link delete = linkTo(methodOn(TaskController.class).deleteTask(userId, task.getId())).withRel("delete");
        task.add(delete);
    }
}
