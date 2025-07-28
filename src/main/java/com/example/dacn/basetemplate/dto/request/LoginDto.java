/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author ADMIN
 */
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    String username, password;
}
