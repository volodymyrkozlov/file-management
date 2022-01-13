package com.volodymyrkozlov.filemanagement.app.repository;

import com.volodymyrkozlov.filemanagement.app.entity.FileOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileOperationRepository extends JpaRepository<FileOperationEntity, Long>,
    QuerydslPredicateExecutor<FileOperationEntity> {
}
