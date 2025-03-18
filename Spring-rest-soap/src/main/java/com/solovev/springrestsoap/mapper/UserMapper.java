package com.solovev.springrestsoap.mapper;

import com.solovev.springrestsoap.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapperAdapter.class})
public interface UserMapper {

    @Mapping(target = "tasks", qualifiedByName = {"setTasks"}, source = "tasks")
    User toModel(com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.User dto);

    List<User> toModel(List<com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.User> dto);

    @Mapping(target = "tasks", qualifiedByName = {"getTasks"}, source = "tasks")
    com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.User toDto(User user);

    List<com.solovev.springrestsoap.dto.soap.generated.localhost._8080.user.User> toDto(List<User> user);
}
