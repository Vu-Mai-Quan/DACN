package com.example.dacn.db1.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.Set;

@Table(name = "tbl_order")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
