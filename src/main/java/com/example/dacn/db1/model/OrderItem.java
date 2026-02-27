package com.example.dacn.db1.model;

import java.math.BigDecimal;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="tbl_order_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
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
