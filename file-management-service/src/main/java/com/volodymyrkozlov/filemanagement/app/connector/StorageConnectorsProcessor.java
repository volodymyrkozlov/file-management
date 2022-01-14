package com.volodymyrkozlov.filemanagement.app.connector;

import com.volodymyrkozlov.filemanagement.app.dto.FileDto;
import com.volodymyrkozlov.filemanagement.app.dto.FileMetaDto;
import com.volodymyrkozlov.filemanagement.app.dto.request.UploadFileRequestDto;
import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.exception.InvalidArgumentException;
import com.volodymyrkozlov.filemanagement.app.mapper.FileMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Validated
@Component
public class StorageConnectorsProcessor {
    private final Map<StorageType, StorageConnector> connectors;
    private final FileMetaService fileMetaService;
    private final FileMapper fileMapper;

    public StorageConnectorsProcessor(final List<StorageConnector> connectors,
                                      final FileMetaService fileMetaService,
                                      final FileMapper fileMapper) {
        this.connectors = connectors.stream()
            .collect(Collectors.toMap(StorageConnector::getStorageType, Function.identity()));
        this.fileMetaService = fileMetaService;
        this.fileMapper = fileMapper;
    }

    public FileDto getFileByGuid(@NotBlank final String guid) {
        final var fileMeta = fileMetaService.findByGuid(guid);

        return this.fetchFile(fileMeta);
    }

    public FileDto getFileByPath(@NotBlank final String path,
                                 @NotBlank final String bucket,
                                 @NotNull final StorageType storage) {
        final var fileMeta = fileMetaService.findByFullPath(fullPath(bucket, path), storage);

        return this.fetchFile(fileMeta);
    }

    public FileMetaDto uploadFile(@NotNull @Valid final UploadFileRequestDto request) {
        final var multipartFile = request.file();
        final var fileName = multipartFile.getResource().getFilename();
        final var directory = request.directory();
        final var bucket = request.bucket();
        final var storage = request.storage();
        final var inputStream = retrieveInputStream(multipartFile);
        final var contentLength = multipartFile.getSize();
        final var contentType = Optional.ofNullable(multipartFile.getContentType())
            .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        final var fullPath = fileFullPath(bucket, directory, fileName);

        this.fetchConnector(storage)
            .uploadFileRequest(fileName, directory, bucket, fullPath, inputStream, contentLength, contentType);
        final var fileMeta =
            fileMetaService.save(fileName, directory, bucket, fullPath, storage, contentLength, contentType);

        return fileMapper.toFileMetaDto(fileMeta);
    }

    private StorageConnector fetchConnector(final StorageType storageType) {
        if (connectors.containsKey(storageType)) {
            return connectors.get(storageType);
        }

        throw new InvalidArgumentException(String.format("Unknown storage [%s]", storageType));
    }

    private FileDto fetchFile(final FileMetaEntity fileMeta) {
        final var inputStream = this.fetchConnector(fileMeta.getStorage()).getFileRequest(fileMeta);

        return fileMapper.toFileDto(fileMeta, inputStream);
    }

    private static InputStream retrieveInputStream(final MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (final IOException e) {
            throw new InvalidArgumentException(e.getMessage());
        }
    }

    private static String fullPath(final String bucket,
                                   final String path) {
        return String.format("/%s%s", bucket, path);
    }

    private static String fileFullPath(final String bucket,
                                       final String directory,
                                       final String fileName) {
        return String.format("%s/%s/%s", bucket, directory, fileName);
    }
}
