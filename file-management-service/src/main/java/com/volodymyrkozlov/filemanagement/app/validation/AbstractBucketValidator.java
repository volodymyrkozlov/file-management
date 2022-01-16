package com.volodymyrkozlov.filemanagement.app.validation;

import com.volodymyrkozlov.filemanagement.app.property.FileManagementProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractBucketValidator implements BucketExistenceValidator {
    private final FileManagementProperties fileManagementProperties;

    @Override
    public boolean isBucketExist(final String bucket) {
        if (fileManagementProperties.getStorages().containsKey(this.getStorageType())) {
            return fileManagementProperties.getStorages().get(this.getStorageType())
                .stream()
                .map(FileManagementProperties.StorageConfigProperty::getBucket)
                .anyMatch(b -> b.equals(bucket));
        }

        return false;
    }
}
