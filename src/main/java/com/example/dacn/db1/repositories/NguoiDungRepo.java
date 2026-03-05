package com.example.dacn.db1.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dacn.db1.model.NguoiDung;
import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NguoiDungRepo extends JpaRepository<NguoiDung, UUID>, JpaSpecificationExecutor<NguoiDungView> {

    @Query("select nd from NguoiDung nd where nd.username = ?1")
    @EntityGraph(value = "nguoiDung.Role")
    Optional<NguoiDung> findByName(String username);

    @Query(name = "NguoiDungView.findUsername")
    Optional<NguoiDungView> findByEmailUser(String username);

    @Query(name = "NguoiDungView.findAll")
    Page<NguoiDungView> findAllNdView(Pageable pageable, Specification<NguoiDungView> specification);

}
