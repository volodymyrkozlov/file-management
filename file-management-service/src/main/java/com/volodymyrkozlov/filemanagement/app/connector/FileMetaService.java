package com.volodymyrkozlov.filemanagement.app.connector;


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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class FileMetaService {
    private final FileMetaRepository fileMetaRepository;
    private final FileOperationRepository fileOperationRepository;

    @Transactional(readOnly = true)
    FileMetaEntity findByGuid(final String guid) {
        final var predicate = QFileEntityFilter.builder()
            .guid(guid)
            .toPredicate();

        return fileMetaRepository.findOne(predicate)
            .orElseThrow(() -> new EntityNotFoundException(String.format("File with guid:[%s] is not found", guid)));
    }

    @Transactional(readOnly = true)
    FileMetaEntity findByFullPath(final String path,
                                  final StorageType storage) {
        return this.findOptionalFileMeta(path, storage)
            .orElseThrow(() -> new EntityNotFoundException(String.format("File:[%s] is not found", path)));
    }

    @Transactional
    FileMetaEntity save(final String fileName,
                        final String directory,
                        final String bucket,
                        final String path,
                        final StorageType storage,
                        final Long contentLength,
                        final String contentType) {

        return this.findOptionalFileMeta(path, storage)
            .map(existingFileMeta -> this.updateFileMeta(existingFileMeta, contentType, contentLength))
            .orElseGet(() -> this.saveInitialFileMeta(
                fileName, directory, bucket, storage, contentLength, contentType, path)
            );
    }

    private FileMetaEntity saveInitialFileMeta(final String fileName,
                                               final String directory,
                                               final String bucket,
                                               final StorageType storage,
                                               final Long contentLength,
                                               final String contentType,
                                               final String path) {
        final var fileMetaEntity = FileMetaEntity.builder()
            .fileName(fileName)
            .directory(directory)
            .bucket(bucket)
            .storage(storage)
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

    private Optional<FileMetaEntity> findOptionalFileMeta(final String path,
                                                          final StorageType storage) {
        final var predicate = QFileEntityFilter.builder()
            .path(path)
            .storage(storage)
            .toPredicate();

        return fileMetaRepository.findOne(predicate);
    }
}
