package com.solovev.springrestsoap.mapper;

import com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.User;
import com.solovev.springrestsoap.model.Task;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Named("UserMapperAdapter")
@Component
@RequiredArgsConstructor
public class UserMapperAdapter {
    private final TaskMapper taskMapper;

    @Named("getTasks")
    public User.Tasks getTasks(List<com.solovev.springrestsoap.model.Task> tasks) {
        User.Tasks result = new User.Tasks();
        var resultTasks = result.getTask();
        tasks.forEach(t -> resultTasks.add(taskMapper.toDto(t)));
        return result;
    }

    @Named("setTasks")
    public List<Task> setTasks(User.Tasks tasks) {
        return tasks.getTask().stream().map(taskMapper::toModel).collect(Collectors.toList());
    }

}
