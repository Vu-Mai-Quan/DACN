package com.example.dacn.controller;

import com.example.dacn.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/image/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping()
    public ResponseEntity<?> createImages(
            @NonNull @RequestParam("file") MultipartFile file,
            @RequestParam String imageId) {
        if (file.getContentType() == null || !file.getContentType().startsWith(
                "image/")) {
            return ResponseEntity.badRequest().body(
                    "{\"message\": \"Định dạng file ko phù hợp\"}");
        }
        var path = imageService.createFile(file, imageId);
        return ResponseEntity.ok(path);
//return ResponseEntity.ok(imageId);
    }

    @GetMapping(path = "{idImage}/{filename:.+}")
    public ResponseEntity<?> getImage(@PathVariable String idImage, @PathVariable String filename) {
        Resource resource = imageService.loadFile(idImage + "/" + filename);
        if (!resource.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
    }

    @GetMapping("{idImage}")
    public ResponseEntity<?> getImage(@PathVariable String idImage) {
        var resource = imageService.loadAll(idImage);
        return ResponseEntity.ok(resource);
    }
    
    @DeleteMapping("{idImage}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long idImage) {
        return imageService.removeFile(idImage) ? ResponseEntity.ok().build()
        		: ResponseEntity.badRequest().build();
    }
    

}
