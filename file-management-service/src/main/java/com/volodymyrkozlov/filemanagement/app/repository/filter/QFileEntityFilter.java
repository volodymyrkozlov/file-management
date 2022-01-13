package com.volodymyrkozlov.filemanagement.app.repository.filter;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.volodymyrkozlov.filemanagement.app.entity.EntityRecordStatus;
import com.volodymyrkozlov.filemanagement.app.entity.QFileMetaEntity;

import java.util.Optional;

public final class QFileEntityFilter {
    private BooleanExpression pred;

    private QFileEntityFilter(final BooleanExpression expression) {
        this.pred = expression;
    }

    public static QFileEntityFilter builder() {
        return new QFileEntityFilter(entity().recordStatus.eq(EntityRecordStatus.A));
    }

    private static QFileMetaEntity entity() {
        return QFileMetaEntity.fileMetaEntity;
    }

    public QFileEntityFilter guid(final String guid) {
        Optional.ofNullable(guid)
            .ifPresent(g -> this.pred = this.pred.and(entity().guid.eq(g)));

        return this;
    }

    public QFileEntityFilter path(final String path) {
        Optional.ofNullable(path)
            .ifPresent(p -> this.pred = this.pred.and(entity().path.eq(p)));

        return this;
    }

    public Predicate toPredicate() {
        return this.pred;
    }
}
