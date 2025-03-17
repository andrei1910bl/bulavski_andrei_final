package com.example.demo.service.mapper;

import com.example.demo.dto.FriendDTO;
import com.example.demo.repository.entity.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    @Mapping(target = "userDTO", source = "user") // Маппим user на userDTO
    @Mapping(target = "friendDTO", source = "friend") // Маппим friend на friendDTO
    FriendDTO toDTO(Friend friend);

    Friend toEntity(FriendDTO friendDTO);
}
