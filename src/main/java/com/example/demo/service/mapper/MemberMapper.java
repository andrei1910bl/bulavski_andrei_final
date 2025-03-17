package com.example.demo.service.mapper;

import com.example.demo.dto.MemberDTO;
import com.example.demo.repository.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(source = "community", target = "communityDTO")
    @Mapping(source = "user", target = "userDTO")
    MemberDTO toDTO(Member member);

    Member toEntity(MemberDTO memberDTO);
}
