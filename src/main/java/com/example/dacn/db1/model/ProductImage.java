package com.example.dacn.db1.model;



import java.math.BigInteger;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name="product_image")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImage extends Image {

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="product_id",nullable = false)
	Product product;
	
	public ProductImage(BigInteger id, String url, Product product) {
		super(id, url);
		this.product = product;
	}

	
	
	
}
