/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.example.dacn.controller;

import com.example.dacn.db1.model.FileEntity;
import com.example.dacn.mapper.ProductMapper;
import com.example.dacn.service.ImageService;
import com.example.dacn.service.ProductService;
import com.example.dacn.template.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/product/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestPart("productDto") ProductDto productDto,
                                           @RequestPart("files") List<MultipartFile> files) {
        var fileEntities = imageService.createMultipleFile(files);
        var listPath = !fileEntities.isEmpty() ? fileEntities.stream().collect(Collectors.toMap(FileEntity::getId,
                FileEntity::getUrl)): null;

        var product = productService.createProduct(productDto, listPath);

        if (product == null) {
            imageService.removeAllById(fileEntities);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(product);
    }

//    @GetMapping
//    ResponseEntity<?> getAllProduct(@PageableDefault() Pageable pageable) {
//
//        return ResponseEntity.ok(productService.getAllProduct(pageable, null));
//    }

    @GetMapping("")
    ResponseEntity<ProductMapper.ProductDetailResponse> disableProduct(@RequestParam String name) {
        return ResponseEntity.ok((ProductMapper.ProductDetailResponse) productService.getProductByName(name));
    }

    @PutMapping
    ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto, null, null) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    ResponseEntity<?> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
