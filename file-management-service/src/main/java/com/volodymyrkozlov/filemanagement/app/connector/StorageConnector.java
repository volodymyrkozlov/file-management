package com.volodymyrkozlov.filemanagement.app.connector;

import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;

import java.io.InputStream;

public interface StorageConnector {

    StorageType getStorageType();

    InputStream getFileRequest(final FileMetaEntity fileMeta);

    void uploadFileRequest(final String fileName,
                           final String directory,
                           final String bucket,
                           final String fullPath,
                           final InputStream inputStream,
                           final Long contentLength,
                           final String contentType);
}
