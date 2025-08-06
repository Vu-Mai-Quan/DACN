/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.sql.Date;

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
    protected Date updateAt;

    @PreUpdate
    protected void setUpdateAt() {
        this.updateAt = new Date(System.currentTimeMillis());
    }
}
