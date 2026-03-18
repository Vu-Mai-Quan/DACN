package com.example.dacn.db1.model;

import com.example.dacn.template.BaseEntity;
import com.example.dacn.template.enumModel.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.Builder.Default;
import lombok.ToString.Exclude;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_user")
@Builder
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "nguoiDung.Role", attributeNodes = {
                @NamedAttributeNode(value = "chucVus")
        })
})
@NoArgsConstructor
public final class NguoiDung extends BaseEntity {

    @Column(unique = true, length = 100, updatable = false)
    @Email(message = "Email đang sai định dạng")
    String username;

    @Setter
    String password;

    @Enumerated(EnumType.STRING)
    @Setter
    UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(
            name = "fk_user_and_store"))
    Store store;

    @JsonIgnore
    @Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "nguoi_dung_va_chuc_vu", joinColumns = @JoinColumn(
            name = "id_nguoi_dung"),
            inverseJoinColumns = @JoinColumn(name = "id_chuc_vu"))
    @Default
    Set<ChucVu> chucVus = new HashSet<>();


}
