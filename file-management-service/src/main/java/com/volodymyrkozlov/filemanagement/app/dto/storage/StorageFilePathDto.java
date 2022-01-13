package com.volodymyrkozlov.filemanagement.app.dto.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StorageFilePathDto {
    private final String fileName;
    private final String directory;
    private final String bucket;

    public String getFullPath() {
        return String.format("%s/%s/%s", this.bucket, this.directory, this.fileName);
    }
}
