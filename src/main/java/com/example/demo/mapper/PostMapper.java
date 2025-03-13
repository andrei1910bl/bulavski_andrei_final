package com.example.demo.mapper;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDTO toDTO(Post post);

    Post toEntity(PostDTO postDTO);
}
