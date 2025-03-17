package com.example.demo.service.mapper;

import com.example.demo.dto.PublicationDTO;
import com.example.demo.repository.entity.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublicationMapper {

    PublicationMapper INSTANCE = Mappers.getMapper(PublicationMapper.class);

    PublicationDTO toDTO(Publication publication);

    Publication toEntity(PublicationDTO publicationDTO);
}
