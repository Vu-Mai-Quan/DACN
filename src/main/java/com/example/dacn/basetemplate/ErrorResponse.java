/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.basetemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ErrorResponse<T> {

    HttpStatus status;
    String message, url;
    @Builder.Default
    Date time = new Date(System.currentTimeMillis());
    T data;

    public ErrorResponse(HttpStatus status, String message, String url) {
        this.status = status;
        this.message = message;
        this.url = url;
    }
}
