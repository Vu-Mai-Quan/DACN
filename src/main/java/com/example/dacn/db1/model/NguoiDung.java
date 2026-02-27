package com.example.dacn.db1.model;

import com.example.dacn.template.enumModel.UserStatus;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.dacn.template.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString.Exclude;
import lombok.experimental.FieldDefaults;

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
public final class NguoiDung extends BaseEntity implements UserDetails {

    @Column(unique = true, length = 100)
    @Email(message = "Email đang sai định dạng")
    String username;

    @Setter
    String password;

    @Enumerated(EnumType.STRING)
    @Setter
    UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "fk_user_and_store"))
    Store store;

    @JsonIgnore
    @Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "nguoi_dung_va_chuc_vu", joinColumns = @JoinColumn(name = "id_nguoi_dung"),
            inverseJoinColumns = @JoinColumn(name = "id_chuc_vu"))
    @Default
    Set<ChucVu> chucVus = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return chucVus.stream().map(cv -> new SimpleGrantedAuthority(cv.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return this.status != UserStatus.DANG_KI;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return status != UserStatus.KHOA;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return status.equals(UserStatus.KICH_HOAT);
    }

    public void addChucVu(ChucVu cv) {
        this.chucVus.add(cv);
    }

}
