package com.volodymyrkozlov.filemanagement.app.validation;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;

public interface BucketExistenceValidator {

    StorageType getStorageType();

    boolean isBucketExist(final String bucket);
}
