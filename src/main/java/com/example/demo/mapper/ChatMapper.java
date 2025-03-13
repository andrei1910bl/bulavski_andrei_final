package com.example.demo.mapper;

import com.example.demo.dto.ChatDTO;
import com.example.demo.model.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    ChatDTO toDTO(Chat chat);

    Chat toEntity(ChatDTO chatDTO);

}
