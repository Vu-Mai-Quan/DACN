package com.example.dacn.repositories;

import com.example.dacn.db1.model.TaiKhoan;
import com.example.dacn.db1.repositories.TaiKhoanRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaiKhoanRepoTest {
    @Autowired
    private TaiKhoanRepo repo;


    @Test
    public void testCallLsTkNoAdmin() {
//        final Set<UUID> ids = Set.of(
//                UUID.fromString("0f492600-5ac8-4afb-b15b-039adbd7bcac"),
//                UUID.fromString("40c3e25f-712c-440f-8d10-c704c3e3a03d"),
//                UUID.fromString("6bee7966-7837-4640-be2e-e36716b814fa")
//        );
//        Set<TaiKhoan> tk = repo.findAllByIdNoRoleAdmin(ids).stream().filter(item -> !item.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))).collect(Collectors.toSet());

    }
}
