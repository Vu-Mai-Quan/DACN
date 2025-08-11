/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.UUID;

/**
 * @author ADMIN
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThongTinNDResponse {

    UUID id;
    String sdt;
    @JsonProperty("ho_ten")
    String hoTen;
    @JsonProperty("ngay_sinh")
    @JsonFormat(timezone = "utf-8", pattern = "dd-MM-yyyy")
    Date ngaySinh;
    String avatar;
    @JsonProperty("tai_khoan")
    TaiKhoanResponese taiKhoan;

}
