/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate.dto.response;

import com.example.dacn.config.annotations.Base64ToBooleanDeserializer;
import com.example.dacn.enumvalues.EnumTypeAccount;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author ADMIN
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
public class TaiKhoanResponese {


    String email;
    @JsonProperty(value = "co_bi_khoa")
    @JsonDeserialize(using = Base64ToBooleanDeserializer.class)
    boolean coBiKhoa;
    @JsonProperty(value = "da_kich_hoat")
    @JsonDeserialize(using = Base64ToBooleanDeserializer.class)
    boolean daKichHoat;

    EnumTypeAccount type;


}
