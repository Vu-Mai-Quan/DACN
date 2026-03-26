package com.example.dacn.db1.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dacn.db1.model.NguoiDung;

public interface NguoiDungRepo extends JpaRepository<NguoiDung, UUID> {

//    @Query("select nd from NguoiDung nd where nd.username = ?1")
    @EntityGraph(value = "nguoiDung.Role")
    Optional<NguoiDung> findByUsername(String username);


    Page<NguoiDung> findAll(Pageable pageable);
   
}
