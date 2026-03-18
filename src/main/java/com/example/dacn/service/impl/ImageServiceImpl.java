package com.example.dacn.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.example.dacn.db1.model.FileEntity;
import com.example.dacn.db1.model.ImageEntity;
import com.example.dacn.db1.repositories.FileRepo;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ImageServiceImpl implements com.example.dacn.service.ImageService {
    private final FileRepo fileRepo;

    @Override
    public FileEntity createFile(MultipartFile fileEntity, String storeDic) {
        String fileName = UUID.randomUUID() + "_" + fileEntity.getOriginalFilename();
        Path folderName = Paths.get(storeDic),
                uploads = Path.of("uploads").resolve(folderName);

        try {
            if (!uploads.toFile().exists()) {
                Files.createDirectory(uploads);
            }
            FileEntity file = ImageEntity.builder().storeRef(storeDic)
                    .url(folderName.resolve(fileName).toString())
                    .systemPath(uploads.resolve(fileName).toAbsolutePath().toString())
                    .isMain(false).build(), fileSuccess = fileRepo.save(file);
            Files.copy(fileEntity.getInputStream(), uploads.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            return fileSuccess;
        } catch (IOException | EntityExistsException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public boolean removeFile(long id) {
        var ref = fileRepo.findById(BigInteger.valueOf(id));
        if (ref.isPresent()) {
            fileRepo.delete(ref.get());
            try {
                Files.deleteIfExists(Paths.get(ref.get().getSystemPath()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
            return true;
        }

        return false;
    }

    @Override
    public Resource loadFile(String url) {
        try {
            return new FileUrlResource(Paths.get("uploads").resolve(url).toUri().toURL());
        } catch (MalformedURLException e) {
            return null;
        }

    }

    @Override
    public List<FileEntity> loadAll(String storeRef) {
//    	var data = fileRepo.findAll();
        return fileRepo.findByStoreRef(storeRef);
//        return data;
    }
}
