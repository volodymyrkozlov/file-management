package com.volodymyrkozlov.filemanagement.app.dto;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;

import java.io.InputStream;
import java.util.Optional;

public record FileDto(String guid,
                      String fileName,
                      String directory,
                      String bucket,
                      StorageType storage,
                      String path,
                      String contentType,
                      String contentLength,
                      InputStream inputStream)  {

    public InputStream inputStream() {
        return Optional.ofNullable(this.inputStream)
            .orElseGet(InputStream::nullInputStream);
    }
}
