package com.example.dacn.db1.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.dacn.template.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.Builder.Default;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "tbl_product")
@NoArgsConstructor
public class Product extends BaseEntity {
	@Column(length = 200, unique = true, nullable = false)
	String name;

	@Column(nullable = false)
	@Lob
	@Enumerated(EnumType.STRING)
	ProductStatus status;

	@Column(name = "image_url", length = 300)
	String imageUrl;

	@Column(precision = 18, scale = 3)
	@Default
	@DecimalMin(
		groups = MinNumberGroup.class, value = "0.0",
		message = "price not small than 0.0")
	double price = 0.0;

	@Column(name = "promotion_price", precision = 18, scale = 3)
	@Default
	@DecimalMin(
		groups = MinNumberGroup.class, value = "0.0",
		message = "promotion_price not small than 0.0")
	/**
	 * Đây là giá sản phẩm đã được khuyến mãi
	 */
	double promotionPrice = 0.0;

	@Default
	@Min(value = 0, message = "Số lượng không được < 0", groups = MinNumberGroup.class)
	int quantity = 0;

	@Default
	@Min(value = 0, message = "Vat không được < 0", groups = MinNumberGroup.class)
	byte vat = 0;

	/**
	 * Đây là số tháng bảo hành
	 */
	@Min(value = 0, message = "Số lượng không được < 0", groups = MinNumberGroup.class)
	@Default
	byte warranty = 0;

	/**
	 * Sản phẩm có ưu tiên hiển thị không
	 */
	@Future(message = "Thời gian phải lớn hơn hiện tại")
	LocalDateTime hot;

	@Lob
	@Column(name = "descriptions")
	String moTa;

	/**
	 * Văn bản chi tiết
	 */
	@Lob
	String detail;

	@Column(name = "meta_keywords", length = 200)
	String metaKeywords;

	@Column(name = "meta_descriptions", length = 200)
	String metaDescriptions;

	@Default
	@Column(name = "view_count")
	int viewCount = 0;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(
		name = "category_id", nullable = false,
		foreignKey = @ForeignKey(foreignKeyDefinition = "fk_product_category"))
	ProductCategory category;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	List<ProductImage> images;

	/**
	 * Nhà sản xuất
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(
		name = "brand_id", nullable = false,
		foreignKey = @ForeignKey(foreignKeyDefinition = "fk_products_brand"))
	Brand brandId;

	/**
	 * Nhà cung cấp
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(
		name = "supplier_id", nullable = false,
		foreignKey = @ForeignKey(foreignKeyDefinition = "fk_products_supplier"))
	Suplier supplierId;

	public static enum ProductStatus {

	}

	public static interface MinNumberGroup {
	}
}
