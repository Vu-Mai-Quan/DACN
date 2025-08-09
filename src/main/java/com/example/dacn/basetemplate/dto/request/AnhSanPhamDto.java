package com.example.dacn.basetemplate.dto.request;

import com.example.dacn.db1.model.AnhSanPham;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO for {@link AnhSanPham}
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record AnhSanPhamDto(String url,@JsonProperty(value = "thu_tu_hien_thi") int thuTuHienThi) implements Serializable {
}