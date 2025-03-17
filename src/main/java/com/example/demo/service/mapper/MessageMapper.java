package com.example.demo.service.mapper;

import com.example.demo.dto.MessageDTO;
import com.example.demo.repository.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDTO toDTO(Message message);

    Message toEntity(MessageDTO messageDTO);
}
