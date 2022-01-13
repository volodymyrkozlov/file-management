package com.volodymyrkozlov.filemanagement.app.service.storage;

import com.volodymyrkozlov.filemanagement.app.dto.storage.request.GetStorageFileRequestDto;
import com.volodymyrkozlov.filemanagement.app.dto.storage.request.PutStorageFileRequestDto;
import com.volodymyrkozlov.filemanagement.app.dto.storage.response.GetFileResponseDto;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.exception.InvalidArgumentException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class LocalStorageService implements StorageService {

    @Override
    public StorageType getType() {
        return StorageType.LOCAL;
    }

    @Override
    public GetFileResponseDto getFile(final GetStorageFileRequestDto request) {
        try {
            return new GetFileResponseDto(request.getFileName(), request.getDirectory(), request.getBucket(),
                new FileInputStream(request.getFullPath()));
        } catch (final FileNotFoundException e) {
            throw new InvalidArgumentException(e);
        }
    }

    @Override
    public void putFile(final PutStorageFileRequestDto request) {
        try {
            FileUtils.copyInputStreamToFile(request.getInputStream(), new File(request.getFullPath()));
        } catch (final IOException e) {
            throw new InvalidArgumentException(e);
        }
    }
}
