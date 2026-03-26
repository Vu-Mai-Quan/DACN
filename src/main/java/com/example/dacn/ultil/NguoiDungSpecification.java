/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.ultil;

import org.springframework.data.jpa.domain.Specification;

import com.example.dacn.db1.model.NguoiDung;
import com.example.dacn.template.enumModel.UserStatus;

/**
 *
 * @author ADMIN
 */
public class NguoiDungSpecification {

    public static Specification<NguoiDung> searchParam(String username
    ) {
        return (root, query, builder) -> builder.like(root.get("username"),
                "%" + username + "%");
    }

    public static Specification<NguoiDung> searchStore(Short storeName) {
        return (root, query, builder) -> {

            return builder.equal(root.get("store").get("id"), storeName);
        };
    }

    public static Specification<NguoiDung> searchParam(UserStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"),
                status.name());
    }

}
