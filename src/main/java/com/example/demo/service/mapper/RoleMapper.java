package com.example.demo.service.mapper;

import com.example.demo.dto.RoleDTO;
import com.example.demo.repository.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(Role role);

    Role toEntity(RoleDTO roleDTO);
}
