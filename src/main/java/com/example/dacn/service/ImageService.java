package com.example.dacn.service;

import com.example.dacn.db1.model.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ImageService {

	FileEntity createFile(ImageRequest imageRequest);

	boolean removeFile(long id);

	Resource loadFile(String url);

	List<FileEntity> createMultipleFile(	Set<ImageRequest> files);

	List<FileEntity> loadAll(Set<Long> urls);

	void removeAllById(Iterable<FileEntity> ids);

	record ImageRequest(Map<String, Object> fileMetadata, MultipartFile files) {
	}
}
