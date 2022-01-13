package com.volodymyrkozlov.filemanagement.app.dto.storage.response;

import com.volodymyrkozlov.filemanagement.app.dto.storage.StorageFilePathDto;
import com.volodymyrkozlov.filemanagement.app.enums.FileParameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class GetFileResponseDto extends StorageFilePathDto {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final InputStream inputStream;
    private final Map<FileParameter, String> fileParams;

    public GetFileResponseDto(@NotBlank final String fileName,
                              @NotBlank final String directory,
                              @NotBlank final String bucket,
                              final InputStream inputStream) {
        this(fileName, directory, bucket, inputStream, null);
    }

    public GetFileResponseDto(@NotBlank final String fileName,
                              @NotBlank final String directory,
                              @NotBlank final String bucket,
                              final InputStream inputStream,
                              final Map<FileParameter, String> fileParams) {
        super(fileName, directory, bucket);
        this.inputStream = inputStream;
        this.fileParams = fileParams;
    }

    public InputStream getInputStream() throws IOException {
        return Optional.ofNullable(this.inputStream)
            .orElseGet(InputStream::nullInputStream);
    }

    public Map<FileParameter, String> getFileParams() {
        return Optional.ofNullable(this.fileParams)
            .map(Collections::unmodifiableMap)
            .orElseGet(Collections::emptyMap);
    }
}
