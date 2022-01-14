package com.volodymyrkozlov.filemanagement.app.dto;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;

public record FileMetaDto(String guid,
                          String fileName,
                          String directory,
                          String bucket,
                          StorageType storage,
                          String path,
                          String contentType,
                          Long contentLength) {
}
