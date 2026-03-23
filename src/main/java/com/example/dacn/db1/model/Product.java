package com.example.dacn.db1.model;

import com.example.dacn.template.BaseEntity;
import com.example.dacn.template.enumModel.ProductStatus;
import com.example.dacn.validations.MinNumberGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "tbl_product", indexes = @Index(columnList = "name ASC, sku DESC"),
        uniqueConstraints = {@UniqueConstraint(
                name = "uq_store_and_sku", columnNames = {"id_store", "sku"})})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder()
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@NamedEntityGraph(name = "Product.detail", attributeNodes = {@NamedAttributeNode("images")})
//@NamedQueries({
//        @NamedQuery(name = "Product.findDetail",
//                query = "SELECT p.name,p.imageUrl,p.sku, p.price, i FROM Product p left join fetch p.images where p.id = ?1"),
//})
public class Product extends BaseEntity {



    @Column(length = 200, unique = true)
    String name;

    @Column(name = "image_url", length = 300)
    String imageUrl;

    @Column(length = 100)
    @Pattern(regexp = "^\\d{2}-[A-Z]{3}-[a-z]{2}$", message = "Cấu trúc sku không hợp lệ")
    String sku;



    @DecimalMin(
            groups = MinNumberGroup.class, value = "0.0",
            message = "price not small than 0.0")
    @Column(precision = 18, scale = 3)
    BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)

    ProductStatus status;


    @JoinColumn(name = "create_by_user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    NguoiDung createBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_store", nullable = false)
    Store store;

    @Min(value = 0, message = "Số lượng không được < 0", groups = MinNumberGroup.class)
    @Default
    int quantity = 0;

    @Version
    Long version;

    @OneToMany(
            mappedBy = "product",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @Setter
    @OrderBy("id.imageId DESC")
    List<ImageProduct> images = new ArrayList<>();

    public void changeQuantity(int delta) {
        if (this.quantity + delta >= 0) {
            this.quantity += delta;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return new EqualsBuilder().append(getId(), product.getId()).append(quantity, product.quantity).append(name, product.name).append(imageUrl, product.imageUrl).append(sku, product.sku).append(price, product.price).append(status, product.status).append(version, product.version).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(name).append(imageUrl).append(sku).append(price).append(status).append(quantity).append(version).toHashCode();
    }
}
