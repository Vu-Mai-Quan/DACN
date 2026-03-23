package com.example.dacn.db1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_chuc_vu")
@NoArgsConstructor
public class ChucVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Enumerated(EnumType.STRING)
    @Getter
    RoleName name;

    @ManyToMany(mappedBy = "chucVus")
    private Set<NguoiDung> nguoiDungs = new HashSet<>();

    public static enum RoleName implements GrantedAuthority {
        SYSTEM_ADMIN, STORE_OWNER, STAFF;

        @Override
        public String getAuthority() {
            return this.name();
        }

        public static final Map<String, RoleName> ROLE_NAME_MAP = Arrays.stream(
                RoleName.values()).collect(Collectors.toMap(
                RoleName::name,
                Function.identity()));

        public static RoleName castStringToRole(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return ROLE_NAME_MAP.get(value.trim());
        }
    ;

    }

    public ChucVu(RoleName name) {
        super();
        this.name = name;
    }

}
