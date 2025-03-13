package com.example.demo.service.mapper;

import com.example.demo.dto.MemberDTO;
import com.example.demo.repository.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDTO toDTO(Member member);

    Member toEntity(MemberDTO memberDTO);
}
