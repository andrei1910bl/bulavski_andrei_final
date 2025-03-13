package com.example.demo.mapper;

import com.example.demo.dto.FriendDTO;
import com.example.demo.model.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    FriendDTO toDTO(Friend friend);

    Friend toEntity(FriendDTO friendDTO);
}
