/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db2.model.compositekey;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author ADMIN
 */
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class IdLogCrud implements Serializable{
    
    UUID idNguoiDung, idTuongTac;
    Date createAt;
}
