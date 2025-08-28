/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate.dto.response;

import com.example.dacn.basetemplate.LogIdResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author ADMIN
 */
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse extends LogIdResponse {

    @JsonProperty(value = "ten_tai_khoan")
    String tenTaiKhoan;
    String token;
    @JsonProperty(value = "refresh_token")
    String refreshToken;
    @JsonIgnore
    boolean taiCheToken;
}
