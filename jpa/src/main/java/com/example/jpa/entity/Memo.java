package com.example.jpa.entity;
//db기준

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 번호(mno), 내용(memo_text-200), 생성날짜(create_date), 수정 날짜(updated_date)
// mno 자동 증가,pk
// 나머지 칼럼 NN(Not Null)

@EntityListeners(value = AuditingEntityListener.class)

@Getter
// @Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long mno;
    @Column(length = 200, nullable = false)
    private String memoText;

    @Column(nullable = false) // 없어도 되지만
    @CreatedDate
    private LocalDateTime createDate;
    @Column(nullable = false) // 넣어도 됨
    @LastModifiedDate
    private LocalDateTime updateDate;

    // setter대신 changeMemoText
    public void changeMemoText(String memoText) {
        this.memoText = memoText;
    }

}
