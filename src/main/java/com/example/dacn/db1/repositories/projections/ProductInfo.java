package com.example.dacn.db1.repositories.projections;

import com.example.dacn.template.enumModel.ProductStatus;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Projection for {@link com.example.dacn.db1.model.Product}
 */
public interface ProductInfo {
    UUID getId();

    String getName();

    String getImageUrl();

    String getSku();

    BigDecimal getPrice();

    ProductStatus getStatus();

    int getQuantity();

    NguoiDungInfo getCreateBy();

    StoreInfo getStore();

    /**
     * Projection for {@link com.example.dacn.db1.model.NguoiDung}
     */
    interface NguoiDungInfo {
        String getUsername();
    }

    /**
     * Projection for {@link com.example.dacn.db1.model.Store}
     */
    interface StoreInfo {
        String getName();
    }
}