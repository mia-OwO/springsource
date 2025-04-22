package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Category extends BaseEntity {
    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // @Builder.Default
    // @OneToMany(mappedBy = "category")
    // private List<Category> categories = new ArrayList<>();
}
