/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntity;
import com.example.dacn.enumvalues.EnumRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
@ToString
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, name = "role_name")
    EnumRole role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<TaiKhoan> taiKhoans = new HashSet<>();

    public Role(EnumRole role) {
        this.role = role;
    }

}
