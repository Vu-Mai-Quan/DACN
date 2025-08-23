package com.example.dacn.db2.model.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class IdBlackListToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "id_user")
    UUID idUser;
    @Column(length = 300)
    String token;
}
