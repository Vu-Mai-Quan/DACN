/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.db1.model.Role;
import com.example.dacn.db1.model.TaiKhoan;
import com.example.dacn.db1.model.ThongTinNguoiDung;
import com.example.dacn.db1.repositories.ChucVuRepo;
import com.example.dacn.db1.repositories.TaiKhoanRepo;
import com.example.dacn.db1.repositories.ThongTinNDRepo;
import com.example.dacn.enumvalues.EnumRole;
import com.example.dacn.enumvalues.EnumTypeAccount;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author ADMIN
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class InitData implements ApplicationRunner {

    final ChucVuRepo chucVuRepo;
    final ThongTinNDRepo thongTinNDRepo;
    final TaiKhoanRepo khoanRepo;
    final PasswordEncoder encode;

    String email, password, sdt;


    @Autowired
    public void setEmailPassword(@Value("${init-data.admin.email}") String email,
                                 @Value("${init-data.admin.password}") String password,
                                 @Value("${init-data.admin.sdt}") String sdt) {
        this.email = email;
        this.password = password;
        this.sdt = sdt;
    }


    @Override
    @Transactional(transactionManager = "db1TransactionManager")
    public void run(ApplicationArguments args) throws Exception {
        khoiTaoChucVuChuaTonTai();
        khoiTaoAdminChuaTonTai();
    }


    private void khoiTaoAdminChuaTonTai() throws Exception {
        if (khoanRepo.kiemTraEmailDaTonTai(email) > 0) {
            if (!khoanRepo.findRolesByEmail(email).isEmpty()) {
                return;
            }
            var tk = khoanRepo.findByEmail(email).get();
            tk.setRoles(chucVuRepo.findAllInName());
            khoanRepo.save(tk);
        } else {
            try {
                var ttnd = thongTinNDRepo.findBySdt(sdt);
                ThongTinNguoiDung nguoiDung = ttnd.orElseGet(ThongTinNguoiDung::new);
                nguoiDung.setAvatar("https://drive.google.com/file/d/1yiZz4XVRyHZjUFi1vTzSsHCr5o7cnBaf/view");
                nguoiDung.setHoTen("Quản trị viên");
                nguoiDung.setSdt(sdt);
                nguoiDung.setNgaySinh(Date.valueOf("2002-06-28"));
                Set<Role> lsRoles = chucVuRepo.findAllInName();
                TaiKhoan khoan = new TaiKhoan();
                khoan.setEmail(email);
                khoan.setMatKhau(encode.encode(password));
                khoan.setDaKichHoat(true);
                khoan.setRoles(lsRoles);
                nguoiDung.setTaiKhoan(khoan);
                khoan.setThongTinNguoiDung(nguoiDung);
                khoan.setType(EnumTypeAccount.CUSTOMER);
                thongTinNDRepo.save(nguoiDung);
            } catch (Exception e) {
                throw new Exception("Lỗi thêm người dùng", e);
            }
        }
    }


    private void khoiTaoChucVuChuaTonTai() {
        try {
            Set<String> mapRoleName = new HashSet<>(chucVuRepo.timHetTenChucVu());
            Set<Role> roles = Arrays.stream(EnumRole.values()).filter(item -> !mapRoleName.contains(item.name()))
                    .map(Role::new)
                    .collect(Collectors.toSet());
            if (roles.isEmpty()) {
                return;
            }
            chucVuRepo.saveAll(roles);
        } catch (Exception e) {
            try {
                throw new Exception("Lỗi thêm chức vụ", e);
            } catch (Exception ex) {
                Logger.getLogger(InitData.class.getName()).log(Level.SEVERE, "chức vụ thêm thất bại {}", ex.getMessage());
            }
        }
    }


}
