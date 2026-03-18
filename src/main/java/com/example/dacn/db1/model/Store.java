package com.example.dacn.db1.model;

import com.example.dacn.template.enumModel.StoreStatus;
import java.time.LocalDateTime;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(length = 150, unique = true, nullable = false)
    String name;

    @Column
    @Enumerated(EnumType.STRING)
    StoreStatus status;

    @Column(name = "create_at", updatable = false)
    @CurrentTimestamp(event = EventType.INSERT)
    LocalDateTime createAt;

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(StoreStatus status) {
        this.status = status;
    }

 
}
