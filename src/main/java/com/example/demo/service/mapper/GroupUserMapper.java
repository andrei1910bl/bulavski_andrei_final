package com.example.demo.service.mapper;

import com.example.demo.dto.GroupUserDTO;
import com.example.demo.repository.entity.GroupUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupUserMapper {

    GroupUserMapper INSTANCE = Mappers.getMapper(GroupUserMapper.class);

    @Mapping(target = "userDTO", source = "user")
    @Mapping(target = "groupDTO", source = "group")
    GroupUserDTO toDTO(GroupUser groupUser);
}
