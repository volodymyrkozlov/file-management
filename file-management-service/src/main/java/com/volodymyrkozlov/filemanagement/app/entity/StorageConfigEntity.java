package com.volodymyrkozlov.filemanagement.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Table(name = "storage_configs")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class StorageConfigEntity extends AuditedEntity {

    @EmbeddedId
    private StorageConfigId id;

    private Boolean isVersioningEnabled;

    @OneToMany(mappedBy = "storageConfig", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StorageConfigOptionEntity> storageConfigOptions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StorageConfigEntity that = (StorageConfigEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
