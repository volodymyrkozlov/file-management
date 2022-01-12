package com.volodymyrkozlov.filemanagement.app.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private String contentType;

    @Column(nullable = false)
    private Long contentLength;

    @Column(nullable = false)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_name")
    @JoinColumn(name = "storage_repository")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StorageConfigEntity storageConfig;

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
