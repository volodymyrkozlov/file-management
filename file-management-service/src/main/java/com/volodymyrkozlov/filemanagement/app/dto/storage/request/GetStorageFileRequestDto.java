package com.volodymyrkozlov.filemanagement.app.dto.storage.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class GetStorageFileRequestDto {
    @NotBlank
    private final String fileName;
    @NotBlank
    private final String directory;
    @NotBlank
    private final String bucket;
    private Map<String, String> configOptions;

    public Map<String, String> getConfigOptions() {
        return Collections.unmodifiableMap(this.configOptions);
    }
}
