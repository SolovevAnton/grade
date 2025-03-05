package com.solovev.springrestsoap.controller.hateoas;

import com.solovev.springrestsoap.controller.TaskController;
import com.solovev.springrestsoap.controller.UserController;
import com.solovev.springrestsoap.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserLinksProvider {

    private final TaskLinksProvider taskLinksProvider;

    public Link addLinks(User user) {
        Link self = addSelfLink(user);
        addDeleteLink(user);
        addAllTasksLink(user);
        addTasksLinks(user);
        return self;
    }

    private Link addSelfLink(User user) {
        Link link = linkTo(UserController.class).slash(user.getId()).withSelfRel();
        user.add(link);
        return link;
    }

    private void addAllTasksLink(User user) {
        Link allTasksLink = linkTo(methodOn(TaskController.class).getTasks(user.getId())).withRel("allTasks");
        user.add(allTasksLink);
    }

    private void addDeleteLink(User user) {
        Link deleteLink = linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete");
        user.add(deleteLink);
    }

    private void addTasksLinks(User user) {
        user.getTasks().forEach(task -> taskLinksProvider.addLinks(task, user.getId()));
    }
}
