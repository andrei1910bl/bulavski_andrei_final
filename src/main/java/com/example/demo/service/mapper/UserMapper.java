package com.example.demo.service.mapper;

import com.example.demo.dto.RegistrationUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

    User toEntity(RegistrationUserDTO registrationUserDTO);



}
