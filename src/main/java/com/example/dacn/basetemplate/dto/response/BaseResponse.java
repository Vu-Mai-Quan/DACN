/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ADMIN
 * @param <T>
 */
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class BaseResponse<T> {

    HttpStatus status;
    T data;
    @Builder.Default
    @JsonProperty(value = "timestamp")
    Date date = new Date(System.currentTimeMillis());

}
