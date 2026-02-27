package com.example.dacn.db1.model;

import java.math.BigDecimal;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Table(name = "tbl_order")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@AllArgsConstructor
public class Order {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @ColumnDefault(value = "0.0")
    @Column(name = "total_amount", precision = 18, scale = 3)
    @Default
    BigDecimal totalAmount = BigDecimal.valueOf(0.0);

    @OneToMany(mappedBy = "order")
    Set<OrderItem> items;

    public static enum OrderStatus {
        CREATED, CONFIRMED, SHIPPED, COMPLETED, CANCELLED;
    }

}
