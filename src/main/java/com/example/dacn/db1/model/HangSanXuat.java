package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "hang_san_xuat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HangSanXuat extends BaseEntityUpdateAt {
    @Column(name = "ten_hang", columnDefinition = "nvarchar(50)", nullable = false)
    String tenHang;
    @Column(name = "mo_ta")
    @Lob
    String moTa;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "hangSanXuat")
    Set<SanPham> sanPham;
}
