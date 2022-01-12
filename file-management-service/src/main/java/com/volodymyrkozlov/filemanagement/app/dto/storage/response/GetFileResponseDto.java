package com.volodymyrkozlov.filemanagement.app.dto.storage.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.InputStream;

public record GetFileResponseDto(@NotBlank String fileName, @NotBlank String directory, @NotBlank String bucket,
                                 @NotNull InputStream data) {
}
