package com.example.dacn.service;

import com.example.dacn.db1.model.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ImageService {

    FileEntity createFile(MultipartFile fileEntity);

    boolean removeFile(long id);

    Resource loadFile(String url);

    List<FileEntity> createMultipleFile(List<MultipartFile> files);

    List<FileEntity> loadAll(Set<Long> urls);

    void removeAllById(Iterable<FileEntity> ids);
}
