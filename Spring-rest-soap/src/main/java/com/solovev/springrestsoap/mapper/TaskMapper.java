package com.solovev.springrestsoap.mapper;

import com.solovev.springrestsoap.model.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toModel(com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.Task dto);

    List<Task> toModel(List<com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.Task> dto);

    com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.Task toDto(Task task);

    List<com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.Task> toDto(List<Task> task);
}
