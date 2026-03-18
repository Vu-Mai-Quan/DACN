package com.example.dacn.db1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "tbl_image")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity extends FileEntity {

    @Column(name = "is_main")
    boolean isMain;


}