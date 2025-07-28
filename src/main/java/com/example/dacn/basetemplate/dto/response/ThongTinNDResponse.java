/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class ThongTinNDResponse {

    UUID id;

    String sdt;
    @JsonProperty(value = "ho_ten")
    String hoTen;
    @JsonProperty(value = "ngau_sinh")
    Date ngaySinh;
    String avatar;
    @JsonProperty(value = "tai_khoan")
    TaiKhoanResponese taiKhoan;
}
