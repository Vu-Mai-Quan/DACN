package com.example.dacn.db1.model;

import com.example.dacn.template.enumModel.StoreStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

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
