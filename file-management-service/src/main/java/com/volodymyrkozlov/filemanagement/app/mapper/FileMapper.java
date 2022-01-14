package com.volodymyrkozlov.filemanagement.app.mapper;

import com.volodymyrkozlov.filemanagement.app.dto.FileDto;
import com.volodymyrkozlov.filemanagement.app.dto.FileMetaDto;
import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import org.mapstruct.Mapper;

import java.io.InputStream;

@Mapper
public interface FileMapper {

    FileMetaDto toFileMetaDto(final FileMetaEntity entity);

    FileDto toFileDto(final FileMetaEntity entity,
                      final InputStream inputStream);
}
