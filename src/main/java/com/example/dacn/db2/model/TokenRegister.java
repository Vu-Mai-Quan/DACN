package com.example.dacn.db2.model;

import com.example.dacn.db2.model.compositekey.IdRegisterToken;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
