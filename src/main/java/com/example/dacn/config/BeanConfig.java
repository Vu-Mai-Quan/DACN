package com.example.dacn.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dacn.db1.model.ChucVu;
import com.example.dacn.db1.model.ChucVu.RoleName;
import com.example.dacn.db1.model.NguoiDung;

import com.example.dacn.db1.repositories.ChucVuRepo;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import com.example.dacn.service.JwtService;
import com.example.dacn.template.enumModel.UserStatus;
import java.nio.file.Path;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class BeanConfig {

    @Bean
    UserDetailsService detailsService(NguoiDungRepo rp) {
        return (username) -> rp.findByEmailUser(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Username or password fail"));
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(BCryptVersion.$2Y);
    }

    @Bean
    UserCache userCache() {
        return new SpringCacheBasedUserCache(new ConcurrentMapCache(
                "userDetailCanche"));
    }
    @Bean
    Path path(){
    return Path.of(System.getProperty("user.dir"));
    }

    @Bean("basicLoginProvider")
    AuthenticationProvider authenticationProvider(
            UserDetailsService detailsService,
            PasswordEncoder encoder, UserCache userCache) {
        var dao = new DaoAuthenticationProvider(encoder);
        dao.setUserDetailsService(detailsService);
        dao.setUserCache(userCache);

        return dao;
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http,
            AuthenticationProvider basicLoginProvider, JwtService<?> jwtService) throws Exception {
        var man = http.getSharedObject(AuthenticationManagerBuilder.class);
        man.authenticationProvider(basicLoginProvider);
        man.authenticationProvider(new BearerAuthenticationProvider(jwtService));
        return man.build();
    }

    @Bean
    CommandLineRunner commandLineRunner(InitSevice initSevice) {
        return (arg) -> {
            initSevice.createChucVu();
            initSevice.createNguoiDung();

        };
    }

    @Service
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class InitSevice {

        NguoiDungRepo dungRepo;
        ChucVuRepo chucVuRepo;
        PasswordEncoder encoder;
        @Value("${init-data.admin.email}")
        String username;

        @Value("${init-data.admin.password}")
        String password;

        @Transactional("db1TrManager")
        public void createChucVu() {
            var lsCvExist = chucVuRepo.findAll();

            var lsCvName = Arrays.asList(RoleName.values()).stream();

            var lsDbNameCv = lsCvExist.stream().map(item -> item.getName().name())
                    .collect(Collectors.toSet());

            var data = lsCvName.filter(item -> !lsDbNameCv.contains(item.name()))
                    .map(ChucVu::new).toList();

            chucVuRepo.saveAll(data);
        }

        @Transactional("db1TrManager")
        public void createNguoiDung() {
            var ls = chucVuRepo.findAll();
            var nd = dungRepo.findByName(username)
                    .orElse(NguoiDung.builder().password(encoder.
                            encode(password))
                            .username(username).status(UserStatus.KICH_HOAT)
                            .chucVus(new HashSet<>(ls)).build());
            if (nd.getChucVus().size() != ls.size()) {
                nd.getChucVus().addAll(ls);
            }
            dungRepo.saveAndFlush(nd);
        }
    }
}
