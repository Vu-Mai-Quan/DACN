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
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ADMIN
 */
@Component
public class InitData implements ApplicationRunner {

    private final ChucVuRepo chucVuRepo;
    private final ThongTinNDRepo thongTinNDRepo;
    private final TaiKhoanRepo khoanRepo;
    private final PasswordEncoder encode;
    private final String email, password;

    public InitData(ChucVuRepo chucVuRepo, ThongTinNDRepo thongTinNDRepo,
                    TaiKhoanRepo khoanRepo, PasswordEncoder encode,
                    @Value("${init-data.admin.email}") String email, @Value("${init-data.admin.password}") String password) {
        this.chucVuRepo = chucVuRepo;
        this.thongTinNDRepo = thongTinNDRepo;
        this.khoanRepo = khoanRepo;
        this.encode = encode;
        this.email = email;
        this.password = password;

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        khoiTaoChucVuChuaTonTai();
        khoiTaoAdminChuaTonTai();
    }


    private void khoiTaoAdminChuaTonTai() throws Exception {
        if (khoanRepo.kiemTraEmailDaTonTai(email) > 0) {
            return;
        }
        try {
            var ttnd = thongTinNDRepo.findBySdt("0989209522");
            ThongTinNguoiDung nguoiDung = ttnd.isPresent() ? ttnd.get() : new ThongTinNguoiDung();
            nguoiDung.setAvatar("https://drive.google.com/file/d/1yiZz4XVRyHZjUFi1vTzSsHCr5o7cnBaf/view");
            nguoiDung.setHoTen("Quản trị viên");
            nguoiDung.setSdt("0989209522");
            nguoiDung.setNgaySinh(new java.sql.Date(2002, 05, 28));
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


    private void khoiTaoChucVuChuaTonTai() {
        try {
            Map<String, String> mapRoleName = chucVuRepo.timHetTenChucVu().stream()
                    .collect(Collectors.toMap(Function.identity(), Function.identity()));
            Set<Role> roles = Arrays.stream(EnumRole.values()).filter(item -> !mapRoleName.containsKey(item.name()))
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
