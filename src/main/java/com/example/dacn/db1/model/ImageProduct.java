package com.example.dacn.db1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Setter
@Entity
@Table(name = "tbl_image_product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageProduct {
    @EmbeddedId
    @JsonProperty("idImage")
    private ProductImageId id;

    @Column(name = "is_main")
    private boolean isMain = false;

    @Getter
    @Column(name = "url_image", length = 200, nullable = false)
    String urlImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @MapsId("productId")
    @JsonIgnore
    Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ImageProduct that = (ImageProduct) o;

        return new EqualsBuilder().append(id, that.id).append(urlImage, that.urlImage).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(urlImage).toHashCode();
    }

    @Embeddable
    @AllArgsConstructor
    @Getter
    @Builder
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class ProductImageId implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @Column(name = "product_id", nullable = false)
        private UUID productId;

        @Column(name = "image_id", nullable = false)
        private Long imageId;
    }

}