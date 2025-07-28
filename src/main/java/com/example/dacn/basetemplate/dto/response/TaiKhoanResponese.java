/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate.dto.response;

import com.example.dacn.enumvalues.EnumTypeAccount;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author ADMIN
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaiKhoanResponese {

    String email;
    @JsonProperty(value = "co_bi_khoa")
    boolean coBiKhoa;
    @JsonProperty(value = "da_kich_hoat")
    boolean daKichHoat;
    EnumTypeAccount type;
    @JsonProperty(value = "danh_sach_chuc_vu")
    Set<String> danhSachChucVu;
}
