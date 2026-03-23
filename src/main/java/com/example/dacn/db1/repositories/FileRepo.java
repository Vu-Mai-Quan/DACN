package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<FileEntity, Long> {

//    List<FileEntity> findAllByStoreRef(Set<UUID> longs);

}
