/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.dto.response.TokenAndExpriredView;
import com.example.dacn.db1.model.compositekey.IdRefreshToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 *
 * @author ADMIN
 */
@Table(name = "refresh_token")
@IdClass(IdRefreshToken.class)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @Id
    @MapsId("idNguoiDung")
    @JoinColumn(name = "id_nguoi_dung", referencedColumnName = "id", nullable = false)
    private TaiKhoan idNguoiDung;
    @Id
    @Column(nullable = false)
    private long exprired;

    @Column(length = 300)
    private String token;

}
