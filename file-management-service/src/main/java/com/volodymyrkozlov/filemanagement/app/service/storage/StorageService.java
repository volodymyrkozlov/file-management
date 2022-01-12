package com.volodymyrkozlov.filemanagement.app.service.storage;


import com.volodymyrkozlov.filemanagement.app.dto.storage.request.GetStorageFileRequestDto;
import com.volodymyrkozlov.filemanagement.app.dto.storage.request.PutStorageFileRequestDto;
import com.volodymyrkozlov.filemanagement.app.dto.storage.response.GetFileResponseDto;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface StorageService {

    StorageType getType();

    GetFileResponseDto getFile(@NotNull @Valid final GetStorageFileRequestDto request);

    void putFile(@NotNull @Valid final PutStorageFileRequestDto request);

}
