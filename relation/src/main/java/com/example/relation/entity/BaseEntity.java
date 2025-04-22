package com.example.relation.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass // 테이블과 매핑되지 않고 자식 클래스에 엔티티의 매핑 정보 상속 (superclass역할만)
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedBy
    @Column(updatable = false)
    private LocalDate creatDate;

    @LastModifiedBy
    private LocalDate updateDate;

}
