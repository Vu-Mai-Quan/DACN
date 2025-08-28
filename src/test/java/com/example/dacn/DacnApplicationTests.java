package com.example.dacn;

import com.example.dacn.repositories.TaiKhoanRepoTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class DacnApplicationTests {
    private final TaiKhoanRepoTest repo;

    @Test
    void contextLoads() {
        repo.testCallLsTkNoAdmin();
    }

}
