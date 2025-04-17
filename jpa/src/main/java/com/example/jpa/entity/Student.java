package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.EnumNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Table(name = "studenttbl")
@Entity // == db table
public class Student {

    @Id // primary key 붙여준다는 의미
    @SequenceGenerator(name = "student_seq_qen", sequenceName = "student_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_qen")
    // @GeneratedValue // create sequence studenttbl_seq start with 1 increment by
    // 50
    private Long id; // id number(19,0) not null primary key(id)

    // @Column(name = "sname", length = 100, nullable = false, unique = true) //
    // column명 바꾸기, 길이 바꾸기, not null, unique제약조건 --> 개별
    // @Column(name = "sname", columnDefinition = "varchar2(20) not null unique") //
    // 한번에 이름 바꿀려면(name = "sname") 요렇게
    @Column(length = 20, nullable = false)
    private String name; // name varchar2(255 char)

    // @Column(columnDefinition = "number(8,0)")
    // private int grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Grade grade;

    // 이름 사용 x -> CONSTRAINT chk_gender 빼
    @Column(columnDefinition = "varchar2(1) CONSTRAINT chk_gender CHECK(gender IN ('M','F'))")
    private String gender;

    // 날짜 데이터 타입
    @CreationTimestamp // insert(초기화)할때
    private LocalDateTime cDateTime; // timestamp(6) -> 기본 // 컬럼명 : C_DATE_TIME

    @UpdateTimestamp // insert + 데이터 수정 할 때마다 변경
    private LocalDateTime uDateTime; // 컬럼명 : U_DATE_TIME --> 대문자 기준으로 _추가

    @CreatedDate
    private LocalDateTime cDateTime2;
    @LastModifiedDate
    private LocalDateTime uDateTime2;

    // enum 정의
    // enum (상수 집합)
    public enum Grade {
        FRESHMAN, SOPHOMORE, JUNIOR, SENIOR // -> 0 1 2 3 값 가짐
    }
}
