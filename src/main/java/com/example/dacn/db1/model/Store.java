package com.example.dacn.db1.model;

import com.example.dacn.template.enumModel.StoreStatus;
import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_store")
//@Comment(value = "Đây là bảng lưu trữ")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class Store {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Short id;

    @Setter
    @Column(length = 150, unique = true, nullable = false, name = "name")
    String storeName;

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    StoreStatus status;

    @Column(name = "create_at", updatable = false)
    @CurrentTimestamp(event = EventType.INSERT)
    LocalDateTime createAt;


}
