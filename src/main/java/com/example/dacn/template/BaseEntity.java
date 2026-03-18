package com.example.dacn.template;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public abstract class BaseEntity {
	@Id
	@UuidGenerator
	@Column(unique = true, updatable = false)
	UUID id;

	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	LocalDateTime createAt;
	@UpdateTimestamp
	LocalDateTime updateAt;


}
