package com.example.dacn.db1.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_chuc_vu")
@NoArgsConstructor
public class ChucVu  {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Byte id;
	
	@Enumerated(EnumType.STRING)
	RoleName name;
	
	@ManyToMany(mappedBy = "chucVus")
	private Set<NguoiDung> nguoiDungs = new HashSet<>();
	
	public static enum RoleName {
		SYSTEM_ADMIN, STORE_OWNER, STAFF;
	}

	
	
	public String getAuthority() {
		return name.name();
	}



	public ChucVu(RoleName name) {
		super();
		this.name = name;
	}
	
}
