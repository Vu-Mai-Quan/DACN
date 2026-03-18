package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface FileRepo extends JpaRepository<FileEntity, BigInteger> {

    List<FileEntity> findByStoreRef(String longs);
    
}
