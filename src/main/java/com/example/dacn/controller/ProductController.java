/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.example.dacn.controller;

import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.dacn.mapper.ProductMapper;
import com.example.dacn.service.ImageService;
import com.example.dacn.service.ImageService.ImageRequest;
import com.example.dacn.service.ProductService;
import com.example.dacn.template.dto.ProductDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

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


    public record ProductRequest(ProductDto productDto, @Schema(
            description = "Nhận vào index của ảnh kèm metadata", example = "{0:{\"isMain\": true}}")
    Map<String, Map<String, Object>> imageMetadata) {
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createProduct(@RequestPart ProductRequest productRequest, @RequestPart List<MultipartFile> images) {
        var imageRequests = new HashSet<ImageRequest>();
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i) != null) {
                imageRequests.add(new ImageRequest(productRequest.imageMetadata.get(Integer.toString(i)),
                        images.get(i)));
            }
        }
        var fileEntities = imageService.createMultipleFile(imageRequests);

        var product = productService.createProduct(productRequest.productDto, !fileEntities.isEmpty() ? fileEntities : null);

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
    ResponseEntity<ProductMapper.ProductDetailResponse> disableProduct(
            @RequestParam String name) {
        return ResponseEntity.ok((ProductMapper.ProductDetailResponse) productService
                .getProductByName(name));
    }

    @PutMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto, UUID id) {
        return productService.updateProduct(productDto, id, null)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    ResponseEntity<?> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
