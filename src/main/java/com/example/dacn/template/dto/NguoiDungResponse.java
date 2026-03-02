package com.example.dacn.template.dto;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class NguoiDungResponse {
	UUID id;
	String username, storeName; Collection<? extends GrantedAuthority> roles; boolean isActive;
}
