package com.example.dacn.db2.model.compositekey;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class IdRegisterToken implements Serializable {
    private UUID idUser;
    private String token;
}
