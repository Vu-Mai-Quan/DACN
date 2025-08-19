package com.example.dacn.db2.model;

import com.example.dacn.basetemplate.BaseEntity;
import com.example.dacn.db2.model.compositekey.IdRegisterToken;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Table(name = "token_register", indexes = @Index(columnList = "token"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenRegister {
    @Id
    @Setter
    private IdRegisterToken id;

    private Date time = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
}
