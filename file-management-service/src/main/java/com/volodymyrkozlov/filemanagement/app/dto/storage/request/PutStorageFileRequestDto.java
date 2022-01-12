package com.volodymyrkozlov.filemanagement.app.dto.storage.request;

import com.volodymyrkozlov.filemanagement.app.validation.ValidContentType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class PutStorageFileRequestDto {
    @NotBlank
    private final String fileName;
    @NotBlank
    private final String directory;
    @NotBlank
    private final String bucket;
    @NotNull
    private final InputStream inputStream;
    @NotNull
    private final Long contentLength;
    @ValidContentType
    @NotBlank
    private final String contentType;
    private Map<String, String> configOptions;

    public Map<String, String> getConfigOptions() {
        return Collections.unmodifiableMap(this.configOptions);
    }
}
