/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntity;
import com.example.dacn.enumvalues.EnumRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "role", indexes = {
    @Index(columnList = "role_name", name = "idx_role_name")})
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter

public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, name = "role_name")
    EnumRole role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<TaiKhoan> taiKhoans = new HashSet<>();

    public Role(UUID id, EnumRole role) {
        super.setId(id);
        this.role = role;
    }

    public Role(EnumRole role) {
        this.role = role;
    }

}
