package com.shoppio.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "amount")
    private Integer amount;

    @Column(name ="image",nullable = false)
    private String image;

    @Column(name ="description",nullable = false, length = 700)
    private String description;

    @Column(name = "added_time", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime addedTime;

    @Column(name = "modify_time")
    @UpdateTimestamp
    private LocalDateTime modifyTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;


}
