package com.example.dacn.basetemplate.dto.request;

import com.example.dacn.enumvalues.EnumRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhanQuyenRq {
    private UUID id;
    private Set<EnumRole> roles;
}
