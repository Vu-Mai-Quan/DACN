/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model.compositekey;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class IdRefreshToken implements Serializable{

    private UUID idNguoiDung;

    private long exprired;

}
