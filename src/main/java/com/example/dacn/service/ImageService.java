package com.example.dacn.service;

import com.example.dacn.db1.model.FileEntity;
import com.example.dacn.db1.model.ImageEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ImageService {

    FileEntity createFile(MultipartFile fileEntity, String storeDic);

    boolean removeFile(long id);

    Resource loadFile(String url);

    //    boolean updateImageRef(String pre, String next);
    List<FileEntity> loadAll(String urls);
}
