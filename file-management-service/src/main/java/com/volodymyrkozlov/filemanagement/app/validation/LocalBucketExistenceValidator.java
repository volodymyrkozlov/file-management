package com.volodymyrkozlov.filemanagement.app.validation;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import com.volodymyrkozlov.filemanagement.app.property.FileManagementProperties;
import org.springframework.stereotype.Component;

@Component
public class LocalBucketExistenceValidator extends AbstractBucketValidator {
    public LocalBucketExistenceValidator(final FileManagementProperties fileManagementProperties) {
        super(fileManagementProperties);
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }
}
