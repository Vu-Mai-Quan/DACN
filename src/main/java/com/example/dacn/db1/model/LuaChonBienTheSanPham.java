package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lua_chon_bien_the_san_pham", schema = "dacn_repair_service_booking_system")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LuaChonBienTheSanPham extends BaseEntityUpdateAt {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id")
    SanPham sanPham;
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    String ten;
}
