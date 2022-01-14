package com.volodymyrkozlov.filemanagement.app.converter;


import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import org.springframework.core.convert.converter.Converter;

public class StorageTypeConverter implements Converter<String, StorageType> {

    @Override
    public StorageType convert(final String source) {
        return StorageType.valueOf(source.toUpperCase());
    }
}
