package com.example.dacn.db1.model;

import com.example.dacn.template.BaseEntity;
import com.example.dacn.template.enumModel.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.Builder.Default;
import lombok.ToString.Exclude;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
@JsonSerialize(include = Inclusion.NON_NULL)
public class NguoiDung extends BaseEntity implements UserDetails{

    @Column(unique = true, length = 100, updatable = false)
    @Email(message = "Email đang sai định dạng")
    String username;

    @Setter
    @JsonIgnore
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
//
//    @ManyToMany(mappedBy = "createBy", fetch = FetchType.LAZY)
//    @JsonIgnore
//    Set<Product> products = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NguoiDung nguoiDung = (NguoiDung) o;

        return new EqualsBuilder().append(getId(), nguoiDung.getId()).append(username, nguoiDung.username).append(password, nguoiDung.password).append(status, nguoiDung.status).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(username).append(password).append(status).toHashCode();
    }

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.chucVus.stream().map(ChucVu::getName).toList();
	}
}
