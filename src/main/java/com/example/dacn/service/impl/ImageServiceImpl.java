package com.example.dacn.service.impl;

import com.example.dacn.db1.model.FileEntity;
import com.example.dacn.db1.model.ImageEntity;
import com.example.dacn.db1.repositories.FileRepo;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ImageServiceImpl implements com.example.dacn.service.ImageService {
    private final FileRepo fileRepo;

    @Override
    @Transactional("db1TrManager")
    public FileEntity createFile(MultipartFile fileEntity) {
        Path uploads = Path.of("uploads");
        try {
            if (!uploads.toFile().exists()) {
                Files.createDirectory(uploads);
            }
            FileEntity file = fileEntityFactory(fileEntity, uploads), fileSuccess = fileRepo.save(file);
            Files.copy(fileEntity.getInputStream(), uploads.resolve(fileSuccess.getUrl()),
                    StandardCopyOption.REPLACE_EXISTING);
            return fileSuccess;
        } catch (IOException | EntityExistsException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    private FileEntity fileEntityFactory(MultipartFile fileEntity, Path uploads) {
        UUID randomUuid = UUID.randomUUID();
        String fileName = randomUuid + "_" + fileEntity.getOriginalFilename();
        return ImageEntity.builder()
                .url(fileName)
                .systemPath(uploads.resolve(fileName).toAbsolutePath().toString())
                .build();
    }

    @Override
    public boolean removeFile(long id) {
        var ref = fileRepo.findById(id);
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
    @Transactional("db1TrManager")
    public List<FileEntity> createMultipleFile(List<MultipartFile> files) {
        Path uploads = Path.of("uploads");
        Map<FileEntity, MultipartFile> item =
                files.stream().collect(Collectors.toMap(mut -> this.fileEntityFactory(mut, uploads),
                        Function.identity()));
        List<FileEntity> lsSuccess = this.fileRepo.saveAll(item.keySet());
        item.forEach((key, value) -> {
            try {
                Files.copy(value.getInputStream(), uploads.resolve(key.getUrl()),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        });
        return lsSuccess;
    }

    @Override
    public List<FileEntity> loadAll(Set<Long> storeRef) {
//    	var data = fileRepo.findAll();
        return fileRepo.findAllById(storeRef);
//        return data;
    }
}
