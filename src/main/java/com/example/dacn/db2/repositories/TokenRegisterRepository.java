package com.example.dacn.db2.repositories;

import com.example.dacn.db2.model.TokenRegister;
import com.example.dacn.db2.model.compositekey.IdRegisterToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface TokenRegisterRepository extends JpaRepository<TokenRegister, IdRegisterToken> {
    @Query(value = "select t from TokenRegister t where t.id in (?1) order by t.time asc")
    Optional<TokenRegister> findByIdUserAndTokenOrderByTimeAsc(@NonNull IdRegisterToken id);

}