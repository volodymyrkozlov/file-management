package com.volodymyrkozlov.filemanagement.app.connector;

import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.exception.InvalidArgumentException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Component
class LocalStorageConnector implements StorageConnector {

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }

    @Override
    public InputStream getFileRequest(final FileMetaEntity fileMeta) {
        try {
            return new FileInputStream(fileMeta.getPath());
        } catch (FileNotFoundException e) {
            throw new InvalidArgumentException(e);
        }
    }

    @Override
    public void uploadFileRequest(final String fileName,
                                  final String directory,
                                  final String bucket,
                                  final String fullPath,
                                  final InputStream inputStream,
                                  final Long contentLength,
                                  final String contentType) {
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(fullPath));
        } catch (final IOException e) {
            throw new InvalidArgumentException(e);
        }
    }
}
