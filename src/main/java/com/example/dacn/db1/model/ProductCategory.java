package com.example.dacn.db1.model;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.example.dacn.template.BaseEntity;
import com.example.dacn.template.enumModel.ProCateStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_ProductCategory", indexes = {
	@Index(columnList = "name, seo_title")
})
@AllArgsConstructor
@Getter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategory extends BaseEntity {
    @Column(length = 100, unique = true)
    String name;

    @Column(name = "seo_title", length = 100)
    String seoTitle;

    @Enumerated(value = EnumType.ORDINAL)
//    @SQLRestriction("status <> 'PENDING'")
    ProCateStatusEnum status;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false)
    @Min(value = 0l)
    int sort;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToStringExclude
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_category_parent"), name = "parent_id", nullable = true)
    ProductCategory parent;

    UUID createBy;

    UUID updateBy;

    @Column(name = "meta_keywords", length = 200)
    String metaKeywords;
    
    @Column(name = "meta_descriptions", length = 200)
    String metaDescriptions;
}
