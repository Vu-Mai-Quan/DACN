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
import com.example.dacn.template.enumModel.UserStatus;
import com.example.dacn.db1.repositories.ChucVuRepo;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import jakarta.persistence.EntityManager;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class BeanConfig {

    @Bean
    UserDetailsService detailsService(NguoiDungRepo R) {
        return (username) -> R.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException("Username or password fail"));
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(BCryptVersion.$2Y);
    }

    @Bean
    UserCache userCache() {
        return new SpringCacheBasedUserCache(new ConcurrentMapCache("userDetailCanche"));
    }

    @Bean("basicLoginProvider")
    AuthenticationProvider authenticationProvider(UserDetailsService detailsService,
            PasswordEncoder encoder, UserCache userCache) {
        var dao = new DaoAuthenticationProvider(encoder);
        dao.setUserDetailsService(detailsService);
        dao.setUserCache(userCache);

        return dao;
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http,
            AuthenticationProvider basicLoginProvider) throws Exception {
        var man = http.getSharedObject(AuthenticationManagerBuilder.class);
        man.authenticationProvider(basicLoginProvider);
        return man.build();
    }

    @Bean
    CommandLineRunner commandLineRunner(InitSevice initSevice) {
        return (arg) -> {
            initSevice.createChucVu();
            initSevice.createNguoiDung();
            initSevice.createView();
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

        EntityManager abstractEntityManagerFactoryBean;

        public void createView() {
            try {
               abstractEntityManagerFactoryBean.getTransaction().begin();
                var dropTableView = abstractEntityManagerFactoryBean.createNativeQuery("""
drop table if exists nguoi_dung_view;
                                                                            """);
              var createNdView =   abstractEntityManagerFactoryBean.createNativeQuery("""
create view if exists nguoi_dung_view as
     select nd.id, nd.username, nd.password, st.name as 'store_name', json_group_array(cv."name"), nd.status from tbl_user nd left join tbl_store st
      on nd.store_id = st.id left join nguoi_dung_va_chuc_vu ndcv on nd.id = ndcv.id_nguoi_dung
   join tbl_chuc_vu cv on ndcv.id_chuc_vu = cv.id order by nd.username;
                                                                   """);
              dropTableView.executeUpdate();
              createNdView.executeUpdate();
              abstractEntityManagerFactoryBean.getTransaction().commit();
            } catch (Exception e) {
                abstractEntityManagerFactoryBean.getTransaction().rollback();
                System.out.println(e.getMessage());
            }
        }

        @Transactional("db1TrManager")
        public void createChucVu() {
            var lsCvExist = chucVuRepo.findAll();

            var lsCvName = Arrays.asList(RoleName.values()).stream();

            var lsDbNameCv = lsCvExist.stream().map(item -> item.getAuthority())
                    .collect(Collectors.toSet());

            var data = lsCvName.filter(item -> !lsDbNameCv.contains(item.name()))
                    .map(ChucVu::new).toList();

            chucVuRepo.saveAll(data);
        }

        @Transactional("db1TrManager")
        public void createNguoiDung() {
            var ls = chucVuRepo.findAll();
            var nd = dungRepo.findByName(username)
                    .orElse(NguoiDung.builder().password(encoder.encode(password))
                            .username(username).status(UserStatus.KICH_HOAT)
                            .chucVus(new HashSet<>(ls)).build());
            if (nd.getChucVus().size() != ls.size()) {
                nd.getChucVus().addAll(ls);
            }
            nd.setStatus(UserStatus.KICH_HOAT);
            nd.setPassword(encoder.encode(password));
            dungRepo.saveAndFlush(nd);
        }
    }
}
