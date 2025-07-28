/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import com.example.dacn.enumvalues.EnumTypeAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author ADMIN
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tai_khoan", indexes = {
    @Index(columnList = "email", name = "email_idx")
})
@FieldDefaults(level = AccessLevel.PRIVATE)

public class TaiKhoan
        extends BaseEntityUpdateAt
        implements UserDetails {

    @OneToOne(fetch = FetchType.LAZY,targetEntity = ThongTinNguoiDung.class)
    @JoinColumn(name = "id")
    @MapsId
    ThongTinNguoiDung thongTinNguoiDung;

    @Column(length = 100, nullable = false)
    @Getter(value = AccessLevel.NONE)
    String email;
    @Column(length = 150, nullable = false, name = "mat_khau")
    @Getter(value = AccessLevel.NONE)
    String matKhau;
    @Column(name = "co_bi_khoa")
    boolean coBiKhoa = false;
    @Column(name = "da_kich_hoat")
    boolean daKichHoat = false;
    @Column(length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    EnumTypeAccount type;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "id_tai_khoan"),
            inverseJoinColumns = @JoinColumn(name = "id_role"),
            name = "tai_khoan_va_chuc_vu")
    @ToString.Exclude
    Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "idNguoiDung", fetch = FetchType.LAZY)
    Set<RefreshToken> refreshTokens = new HashSet<>();

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role
                -> new SimpleGrantedAuthority("ROLE_%s".formatted(role.getRole().name())))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.matKhau;// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getUsername() {
        return this.email; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.coBiKhoa;// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isEnabled() {
        return daKichHoat;
    }
}
