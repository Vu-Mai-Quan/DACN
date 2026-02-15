package com.example.dacn.db1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Đây là thực thể NSX
 */
@Entity
@Table(name="tbl_brand")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Short id;
	
	@Column(length = 100)
	String name;
	
}
