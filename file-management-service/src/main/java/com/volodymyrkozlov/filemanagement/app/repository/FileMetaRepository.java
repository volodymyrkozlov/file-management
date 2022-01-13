package com.volodymyrkozlov.filemanagement.app.repository;

import com.volodymyrkozlov.filemanagement.app.entity.FileMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileMetaRepository extends JpaRepository<FileMetaEntity, String>,
    QuerydslPredicateExecutor<FileMetaEntity> {
}
