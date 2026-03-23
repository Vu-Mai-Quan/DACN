package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FileRepo extends JpaRepository<FileEntity, Long> {

//    List<FileEntity> findAllByStoreRef(Set<UUID> longs);

}
