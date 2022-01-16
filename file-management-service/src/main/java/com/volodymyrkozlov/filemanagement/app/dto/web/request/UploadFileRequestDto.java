package com.volodymyrkozlov.filemanagement.app.dto.web.request;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.validation.BucketExists;
import com.volodymyrkozlov.filemanagement.app.validation.MultipartFileIsNotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@BucketExists
public record UploadFileRequestDto(@NotBlank @Pattern(regexp = "^[^/].*[^/]$") String directory,
                                   @NotBlank String bucket,
                                   @NotNull StorageType storage,
                                   @NotNull @MultipartFileIsNotEmpty MultipartFile file) {
}
