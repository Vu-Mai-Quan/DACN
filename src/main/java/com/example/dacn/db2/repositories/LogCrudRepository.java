package com.example.dacn.db2.repositories;

import com.example.dacn.db2.model.LogCrud;
import com.example.dacn.db2.model.compositekey.IdLogCrud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogCrudRepository extends JpaRepository<LogCrud, IdLogCrud> {

}