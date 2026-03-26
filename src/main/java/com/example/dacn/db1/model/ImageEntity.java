package com.example.dacn.db1.model;

import java.util.Map;

import com.example.dacn.ultil.JsonConvert;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "tbl_image")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageEntity extends FileEntity {

	@Default
	int width = 100;
	@Default
	int height = 100;
	
	@Lob
	@Column
	@Convert(converter = JsonConvert.class)
	private Map<String, Object> metadata;
	
	
}