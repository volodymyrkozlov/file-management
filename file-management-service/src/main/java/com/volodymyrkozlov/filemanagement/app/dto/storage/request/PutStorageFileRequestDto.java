package com.volodymyrkozlov.filemanagement.app.dto.storage.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PutStorageFileRequestDto extends GetStorageFileRequestDto {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final InputStream inputStream;

    public PutStorageFileRequestDto(@NotBlank final String fileName,
                                    @NotBlank final String directory,
                                    @NotBlank final String bucket,
                                    final InputStream inputStream) {
        super(fileName, directory, bucket);
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() throws IOException {
        return Optional.ofNullable(this.inputStream)
            .orElseGet(InputStream::nullInputStream);
    }
}
