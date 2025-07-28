/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import java.sql.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ADMIN
 */
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class BaseEntityUpdateAt extends BaseEntity {

    @Column(name = "update_at")
    @Setter(AccessLevel.NONE)
    protected Date update_at;

    @PreUpdate
    protected void setUpdateAt() {
        this.update_at = new Date(System.currentTimeMillis());
    }
}
