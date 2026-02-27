package com.example.dacn.db1.model;

import com.example.dacn.validations.MinNumberGroup;
import com.example.dacn.template.enumModel.ProductStatus;
import java.math.BigDecimal;

import com.example.dacn.template.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_product", indexes = @Index(columnList = "name ASC"), uniqueConstraints = {
    @UniqueConstraint(name = "uq_store_and_sku", columnNames = {"id_store", "sku"})
})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(length = 200, unique = true, nullable = false)
    String name;

    @Column(name = "image_url", length = 300)
    String imageUrl;

    @Column(length = 100, nullable = false)
    @Pattern(regexp = "^\\d{2}-[A-Z]{3}-[a-z]{2}$", message = "Cấu trúc sku không hợp lệ")
    String sku;

    @DecimalMin(
            groups = MinNumberGroup.class, value = "0.0", message = "price not small than 0.0")
    @Column(precision = 18, scale = 3)
    BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ProductStatus status;

    @ManyToOne()
    @JoinColumn(name = "create_by_user_id", nullable = false)
    NguoiDung createBy;

    @ManyToOne
    @JoinColumn(name = "id_store", unique = true, nullable = false)
    Store store;

    @Min(value = 0, message = "Số lượng không được < 0", groups = MinNumberGroup.class)
    @Default
    int quantity = 0;

    @Version
    Long version;

    public void setStock(int quantity) {
        if (quantity > this.quantity) {
            throw new RuntimeException("Số lượng hàng mua lớn hơn tổng sản phẩm tồn tại");
        }
        this.quantity -= quantity;
    }

}
