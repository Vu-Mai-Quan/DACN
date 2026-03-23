/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model.viewmodel;

import com.example.dacn.db1.model.ChucVu;
import com.example.dacn.template.enumModel.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Immutable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "nguoi_dung_view")
@NamedQueries({
    @NamedQuery(name = "NguoiDungView.findAll",
            query = "SELECT n FROM NguoiDungView n"),
    @NamedQuery(name = "NguoiDungView.findUsername",
            query = "SELECT n FROM NguoiDungView n where n.username = ?1")
})
@Immutable
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NguoiDungView implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    UUID id;
    @Size(max = 100)
    @Column(name = "username")
    String username;
    @Size(max = 255)
    @Column(name = "password")
    @JsonIgnore
    String password;
    @Size(max = 150)
    @Column(name = "store_name")
    String storeName;
    @Size(max = 2000000000)
    @Column(name = "roles")
    @JsonIgnore
    String roles;
    @Size(max = 255)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    UserStatus status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var rolesString = this.roles.split(",");
        List<ChucVu.RoleName> list = new ArrayList<>();
        for (var item : rolesString) {
            var cast = ChucVu.RoleName.castStringToRole(item);
            if (cast != null) {
                list.add(cast);
            }
        }
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return this.status != UserStatus.DANG_KI;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return status != UserStatus.KHOA;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return status.equals(UserStatus.KICH_HOAT);
    }

    @Override
    public String toString() {
        return "{id: %s,username: %s, password: %s, roles: %s, isActive: %s}".
                formatted(id, username, password, getAuthorities(), isEnabled()); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
