package com.volodymyrkozlov.filemanagement.app.dto.storage.request;

import com.volodymyrkozlov.filemanagement.app.dto.storage.StorageFilePathDto;
import com.volodymyrkozlov.filemanagement.app.enums.FileParameter;
import com.volodymyrkozlov.filemanagement.app.enums.StorageConfigOption;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class GetStorageFileRequestDto extends StorageFilePathDto {
    private Map<FileParameter, String> fileParams;
    private Map<StorageConfigOption, String> configOptions;

    public GetStorageFileRequestDto(@NotBlank final String fileName,
                                    @NotBlank final String directory,
                                    @NotBlank final String bucket) {
        super(fileName, directory, bucket);
    }

    public void addStorageConfigOption(final StorageConfigOption option,
                                       final String value) {
        Optional.ofNullable(this.configOptions)
            .ifPresentOrElse(options -> options.put(option, value), () -> {
                this.configOptions = new HashMap<>();
                this.configOptions.put(option, value);
            });
    }

    public void addFileParam(final FileParameter param,
                             final String value) {
        Optional.ofNullable(this.fileParams)
            .ifPresentOrElse(params -> params.put(param, value), () -> {
                this.fileParams = new HashMap<>();
                this.fileParams.put(param, value);
            });
    }

    public Map<StorageConfigOption, String> getConfigOptions() {
        return Optional.ofNullable(this.configOptions)
            .map(Collections::unmodifiableMap)
            .orElseGet(Collections::emptyMap);
    }

    public Map<FileParameter, String> getFileParams() {
        return Optional.ofNullable(this.fileParams)
            .map(Collections::unmodifiableMap)
            .orElseGet(Collections::emptyMap);
    }
}
