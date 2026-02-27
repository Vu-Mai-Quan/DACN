/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import org.hibernate.annotations.Immutable;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "nguoi_dung_view")
@NamedQueries({
    @NamedQuery(name = "NguoiDungView.findAll", query = "SELECT n FROM NguoiDungView n")})
@Immutable
public class NguoiDungView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2000000000)
    @Column(name = "id")
    @Id
    private String id;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @Size(max = 100)
    @Column(name = "username")
    private String username;

    public NguoiDungView() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
