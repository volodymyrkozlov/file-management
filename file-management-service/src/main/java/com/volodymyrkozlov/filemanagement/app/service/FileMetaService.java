package com.volodymyrkozlov.filemanagement.app.service;


import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import com.volodymyrkozlov.filemanagement.app.entity.FileOperationEntity;
import com.volodymyrkozlov.filemanagement.app.enums.FileOperation;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.repository.FileMetaRepository;
import com.volodymyrkozlov.filemanagement.app.repository.FileOperationRepository;
import com.volodymyrkozlov.filemanagement.app.repository.filter.QFileEntityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class FileMetaService {
    private final FileMetaRepository fileMetaRepository;
    private final FileOperationRepository fileOperationRepository;

    @Transactional(readOnly = true)
    public FileMetaEntity findByGuid(@NotBlank final String guid) {
        final var predicate = QFileEntityFilter.builder()
            .guid(guid)
            .toPredicate();

        return fileMetaRepository.findOne(predicate)
            .orElseThrow(() -> new EntityNotFoundException(String.format("File with guid:[%s] is not found", guid)));
    }

    @Transactional(readOnly = true)
    public FileMetaEntity findByFullPath(@NotBlank final String path) {
        return this.findOptionalFileMeta(path)
            .orElseThrow(() -> new EntityNotFoundException(String.format("File:[%s] is not found", path)));
    }

    @Transactional
    public FileMetaEntity save(@NotBlank final String fileName,
                               @NotBlank final String directory,
                               @NotBlank final String bucket,
                               @NotBlank final String path,
                               @NotNull final StorageType storageType,
                               @NotNull final Long contentLength,
                               @NotBlank final String contentType) {

        return this.findOptionalFileMeta(path)
            .map(existingFileMeta -> this.updateFileMeta(existingFileMeta, contentType, contentLength))
            .orElseGet(() -> this.saveInitialFileMeta(
                fileName, directory, bucket, storageType, contentLength, contentType, path)
            );
    }

    private FileMetaEntity saveInitialFileMeta(final String fileName,
                                               final String directory,
                                               final String bucket,
                                               final StorageType storageType,
                                               final Long contentLength,
                                               final String contentType,
                                               final String path) {
        final var fileMetaEntity = FileMetaEntity.builder()
            .fileName(fileName)
            .directory(directory)
            .bucket(bucket)
            .storage(storageType)
            .contentType(contentType)
            .contentLength(contentLength)
            .path(path)
            .build();

        final var savedFileMeta = fileMetaRepository.save(fileMetaEntity);
        this.saveFileOperationEntity(fileMetaEntity, FileOperation.CREATE);

        return savedFileMeta;
    }

    private FileMetaEntity updateFileMeta(final FileMetaEntity fileMetaEntity,
                                          final String contentType,
                                          final Long contentLength) {
        fileMetaEntity.setContentType(contentType);
        fileMetaEntity.setContentLength(contentLength);

        final var updatedFileMeta = fileMetaRepository.save(fileMetaEntity);
        this.saveFileOperationEntity(fileMetaEntity, FileOperation.UPDATE);

        return updatedFileMeta;
    }

    private void saveFileOperationEntity(final FileMetaEntity fileMetaEntity,
                                         final FileOperation fileOperation) {
        final var fileOperationEntity = FileOperationEntity.builder()
            .file(fileMetaEntity)
            .fileOperation(fileOperation)
            .build();

        fileOperationRepository.save(fileOperationEntity);
    }

    private Optional<FileMetaEntity> findOptionalFileMeta(final String path) {
        final var predicate = QFileEntityFilter.builder()
            .path(path)
            .toPredicate();

        return fileMetaRepository.findOne(predicate);
    }
}
