package com.example.dacn.db1.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dacn.db1.model.NguoiDung;

public interface NguoiDungRepo extends JpaRepository<NguoiDung, UUID> {
	
	@Query("select nd from NguoiDung nd where nd.username = :username")
	@EntityGraph(value = "nguoiDung.Role")
	Optional<NguoiDung> findByName(String username);

}
