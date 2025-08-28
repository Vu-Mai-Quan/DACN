/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.dto.response.TokenAndExpriredView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import java.util.UUID;

/**
 * @author ADMIN
 */
@Table(name = "refresh_token")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedNativeQueries({
        @NamedNativeQuery(name = "RefreshToken.GetTọkenAndExprired", query = "select rft.token, rft.exprired "
                + "from refresh_token rft where rft.id_nguoi_dung = :id order by rft.exprired desc limit 1", resultSetMapping = "RefreshToken.TokenAndExpriredView")
})
@SqlResultSetMappings({
        @SqlResultSetMapping(classes = @ConstructorResult(targetClass = TokenAndExpriredView.class, columns = {
                @ColumnResult(name = "token", type = String.class),
                @ColumnResult(name = "exprired", type = Long.class)
        }), name = "RefreshToken.TokenAndExpriredView")
})
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private TaiKhoan idNguoiDung;

    @Column(nullable = false)
    private long exprired;

    @Column(length = 300)
    private String token;

    public RefreshToken(TaiKhoan idNguoiDung, long exprired, String token) {
        this.idNguoiDung = idNguoiDung;
        this.exprired = exprired;
        this.token = token;
    }
}
