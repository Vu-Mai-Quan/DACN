/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.ultil;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import com.example.dacn.template.enumModel.UserStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author ADMIN
 */
public class NdViewSpecification {

    public static Specification<NguoiDungView> searchParam(String username
    ) {
        return (root, query, builder) -> builder.like(root.get("name"),
                "%" + username + "%");
    }

    public static Specification<NguoiDungView> searchStore(String storeName) {
        return (root, query, builder) -> {

            return builder.like(root.get("storeName"),
                    "%" + storeName);
        };
    }

    public static Specification<NguoiDungView> searchParam(UserStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"),
                status.name());
    }

}
