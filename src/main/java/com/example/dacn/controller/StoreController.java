/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.dacn.controller;

import com.example.dacn.db1.model.Store;
import com.example.dacn.service.StoreSevice;
import com.example.dacn.template.enumModel.StoreStatus;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/store/")
public class StoreController {

    private final StoreSevice storeSevice;

    public StoreController(StoreSevice storeSevice) {
        this.storeSevice = storeSevice;
    }

    @PostMapping("jwt/create")
//    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    ResponseEntity<String> create(@RequestBody Store store) {
        store.setName(store.getName().trim());
        boolean res = storeSevice.createStore(store);
        return ResponseEntity.status(res ? 200 : 404).body(
                res ? "{'message':'Tạo thành công'}" : "{'message': 'Thất bại'}");
    }

    @GetMapping("public/get")
    ResponseEntity<List<Store>> getAll() {
        return ResponseEntity.ok(storeSevice.getAll());
    }

    @PutMapping("jwt/status/{id}")
//    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    ResponseEntity<String> updateStatus(@PathVariable() Short id,
            @RequestParam() StoreStatus status) {
        boolean res = storeSevice.updateStatusStore(id, status);
        return ResponseEntity.status(res ? 200 : 404).body(
                res ? "{'message':'Tạo thành công'}" : "{'message': 'Thất bại'}");
    }

    @PutMapping("jwt/rename/{id}")
//    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    ResponseEntity<String> rename(@PathVariable() Short id,
            @RequestParam() String name) {
        boolean res = storeSevice.renameStore(name, id);
        return ResponseEntity.status(res ? 200 : 404).body(
                res ? "{'message':'Tạo thành công'}" : "{'message': 'Thất bại'}");
    }
}
