package com.example.dacn.db1.model;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PROTECTED)
@DiscriminatorColumn(
	length = 20, name = "image_type", discriminatorType = DiscriminatorType.CHAR)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	BigInteger id;

	@Column(nullable = false)
	String url;

}
