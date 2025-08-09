package com.example.dacn.db1.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "anh_san_pham", schema = "dacn_repair_service_booking_system")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnhSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id")
    SanPham sanPham;
    @Column(nullable = false, length = 300)
    String url;
    @Column(nullable = false, name = "display_order")
    int thuTuHienThi;
}
