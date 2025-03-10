package com.example.demo.mapper;

import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRoleMapper {

    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleDTO toDTO(UserRole userRole);

    UserRole toEntity(UserRoleDTO userRoleDTO);
}
