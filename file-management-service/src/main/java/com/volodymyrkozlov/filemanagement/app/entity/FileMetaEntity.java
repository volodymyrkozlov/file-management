package com.volodymyrkozlov.filemanagement.app.entity;

import com.volodymyrkozlov.filemanagement.app.enums.StorageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "files_meta")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class FileMetaEntity extends AuditedEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String guid;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String directory;

    @Column(nullable = false)
    private String bucket;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageType storage;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long contentLength;

    @Column(nullable = false)
    private String path;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        FileMetaEntity that = (FileMetaEntity) o;
        return guid != null && Objects.equals(guid, that.guid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
