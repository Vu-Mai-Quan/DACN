package com.example.dacn.controller;

import com.example.dacn.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/image/")
@RequiredArgsConstructor
public class ImageController {
	private final ImageService imageService;

	@PostMapping()
	public ResponseEntity<?> createImages(
			@NonNull @RequestParam ImageService.ImageRequest imageRequest) {
		if (imageRequest.files().getContentType() == null
				|| !imageRequest.files().getContentType().startsWith("image/")) {
			return ResponseEntity.badRequest()
					.body(Map.of("message","Định dạng file không phù hợp"));
		}
		var path = imageService.createFile(imageRequest);
		return ResponseEntity.ok(path);
//return ResponseEntity.ok(imageId);
	}

	@GetMapping(path = "{filename:.+}")
	public ResponseEntity<?> getImage(@PathVariable String filename) {
		Resource resource = imageService.loadFile(filename);
		if (!resource.exists())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
	}

	@GetMapping
	public ResponseEntity<?> getImage(@RequestBody Set<Long> idImage) {
		var resource = imageService.loadAll(idImage);
		return ResponseEntity.ok(resource);
	}

	@DeleteMapping("{idImage}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long idImage) {
		return imageService.removeFile(idImage) ? ResponseEntity.ok().build()
				: ResponseEntity.badRequest().build();
	}

}
