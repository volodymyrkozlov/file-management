package com.volodymyrkozlov.filemanagement.app.dto.request;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UploadFileRequestDto(@NotBlank String directory,
                                   @NotBlank String bucket,
                                   @NotNull StorageType storage,
                                   @NotNull MultipartFile file) {
}
