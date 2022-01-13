package com.volodymyrkozlov.filemanagement.app.property;

import com.volodymyrkozlov.filemanagement.app.enums.StorageConfigOption;
import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "file-management")
public class FileManagementProperties {

    private final Map<StorageType, List<StorageConfigProperty>> storages = new HashMap<>();

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class StorageConfigProperty {
        private String bucket;
        private Map<StorageConfigOption, String> options;
    }
}
