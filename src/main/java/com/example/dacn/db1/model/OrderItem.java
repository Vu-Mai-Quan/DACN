package com.example.dacn.db1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name="tbl_order_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false)
	@Min(value = 0, message = "Ít nhất 0")
	@ColumnDefault(value = "0")
	@Default
	int quantity= 0;
	
	@Column(name = "price_at_purchase", precision = 18, scale = 3)
	@DecimalMin(value = "0.000")
	@Default
	BigDecimal price = BigDecimal.valueOf(0.00);
	
	@ManyToOne()
	@JoinColumn(nullable = false, name = "product_id")
	Product product;
	
	@ManyToOne
	@JoinColumn(name="order_id", nullable = false)
	Order order;
	
	
}
