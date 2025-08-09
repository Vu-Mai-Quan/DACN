package com.example.dacn.db1.model.viewmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Mapping for DB view
 * create view  thong_tin_nd_va_chuc_vu as select `tt`.`id`                                                     AS `id`,
 * `tt`.`ho_ten`                                                 AS `ho_ten`,
 * `tt`.`avatar`                                                 AS `avatar`,
 * `tt`.`ngay_sinh`                                              AS `ngay_sinh`,
 * `tt`.`sdt`                                                    AS `sdt`,
 * json_object('email', `tk`.`email`,
 * 'type', `tk`.`type`, 'co_bi_khoa', `tk`.`co_bi_khoa`, 'da_kich_hoat',                                                `tk`.`da_kich_hoat`, 'role_list', (select JSON_ARRAYAGG(r.role_name)
 * from tai_khoan_va_chuc_vu tkr
 * join role r on tkr.id_role = r.id                                                                                   where (tkr.id_tai_khoan = tt.id))) as `tai_khoan` from (thong_tin_nguoi_dung tt join tai_khoan tk on tt.id = tk.id)
 * group by tt.id;
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Immutable
@Table(name = "thong_tin_nd_va_chuc_vu")
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "ThongTinNdVaChucVu.findAll", query = "select u from ThongTinNdVaChucVu u")
})
public class ThongTinNdVaChucVu implements Serializable {
    @Serial
    private static final long serialVersionUID = -4138241098001404188L;
    @Column(name = "id", nullable = false, length = 16)
    @Id
    private UUID id;

    @Column(name = "ho_ten", length = 50)
    private String hoTen;

    @Column(name = "avatar", length = 225)
    private String avatar;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "sdt", nullable = false, length = 13)
    private String sdt;

    @Size(max = 100)
    @NotNull
    @Column(name = "tai_khoan", nullable = false)
    private String taiKhoan;


}