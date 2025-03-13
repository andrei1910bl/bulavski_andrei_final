package com.example.demo.service.mapper;

import com.example.demo.dto.GroupDTO;
import com.example.demo.repository.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDTO toDTO(Group group);

    Group toEntity(GroupDTO groupDTO);
}
