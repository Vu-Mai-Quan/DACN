package com.example.dacn.db2.repositories;

import com.example.dacn.db2.model.LogCrud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogCrudRepository extends JpaRepository<LogCrud, UUID> {

}