package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "bien_the_san_pham", schema = "dacn_repair_service_booking_system", indexes = {@Index(columnList = "sku, gia_ca")})
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BienTheSanPham extends BaseEntityUpdateAt {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id")
    SanPham sanPham;
    @Column(name = "sku", nullable = false)
    String sku;
    @Column(name = "gia_ca")
    @DecimalMin(value = "0.0")
    @ColumnDefault(value = "0.0")
    BigDecimal price;
    @Column(name = "ton_kho")
    @Min(0)
    @ColumnDefault(value = "0")
    int tonKho;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "variant_option_product", joinColumns = {@JoinColumn(name = "variant_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_value_id")})
    Set<GiaTriBienTheSP> giaTriBienTheSPs;
}
