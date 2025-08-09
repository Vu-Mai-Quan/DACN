package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "gia_tri_bien_the_san_pham", schema = "dacn_repair_service_booking_system")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiaTriBienTheSP extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_value_id", referencedColumnName = "id")
    LuaChonBienTheSanPham optionValue;
    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    String value;
    @ManyToMany(mappedBy = "giaTriBienTheSPs", fetch = FetchType.LAZY)
    Set<BienTheSanPham> bienTheSanPhams;
}
