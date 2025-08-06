package com.example.dacn.basetemplate.dto.request;

import com.example.dacn.config.annotations.PasswordMatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@PasswordMatch(passwordField = "password", confirmPasswordField = "retypePassword", message = "Mật khẩu phải trùng nhau")
public class RegisterDto {

    @Email
    @NotBlank(message = "Không được để trống email")
    String email;
    @Pattern(regexp = "^(?=\\D*\\d)(?=[^a-z]*[a-z])[a-z0-9]{6,13}$", message = "Mật khẩu phải từ 6-13 kí tự bao gồm chữ thường và số, không bao gồm chữ cái hoa và kí tự đặc biệt")
    @NotBlank(message = "Không được để trống mật khẩu")
    String password;
    @JsonProperty(value = "retype_password")
    String retypePassword;
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    @NotBlank(message = "Không được để trống sdt")
    String sdt;

}
