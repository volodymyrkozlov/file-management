package com.volodymyrkozlov.filemanagement.app.entity;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StorageConfigId implements Serializable {
    @Enumerated(EnumType.STRING)
    private StorageType name;
    private String repository;

    public static StorageConfigId of(final StorageType name,
                                     final String repository) {
        return new StorageConfigId(name, repository);
    }
}
