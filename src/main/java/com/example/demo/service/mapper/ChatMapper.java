package com.example.demo.service.mapper;

import com.example.demo.dto.ChatDTO;
import com.example.demo.repository.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(source = "firstUser", target = "firstUser")
    @Mapping(source = "secondUser", target = "secondUser")
    ChatDTO toDTO(Chat chat);

    Chat toEntity(ChatDTO chatDTO);

}
