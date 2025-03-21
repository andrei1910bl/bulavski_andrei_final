package com.example.demo.service.mapper;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.repository.entity.Community;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommunityMapper {

    CommunityMapper INSTANCE = Mappers.getMapper(CommunityMapper.class);

    CommunityDTO toDTO(Community community);

    Community toEntity(CommunityDTO communityDTO);
}
