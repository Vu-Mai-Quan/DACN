/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.impl;

import com.example.dacn.db1.model.Store;
import com.example.dacn.db1.repositories.StoreRepo;
import com.example.dacn.service.StoreSevice;
import com.example.dacn.template.enumModel.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author ADMIN
 */
@Service

public class StoreSeviceImpl implements StoreSevice {

    private final StoreRepo storeRepo;

    public StoreSeviceImpl(StoreRepo storeRepo) {
        this.storeRepo = storeRepo;
    }

    @Override
    public boolean createStore(Store store) {
        if (store.getStoreName() == null || store.getStatus() == null) {
            return false;
        }
        if (this.storeRepo.existsByStoreName(
                        store.getStoreName().trim())) {
            return false;
        }
        storeRepo.save(store);
        return true;
    }

    @Override
    public boolean renameStore(String name, short id) {
        var store = storeRepo.findById(id);
        if (store.isEmpty()) {
            return false;
        }
        store.get().setStoreName(name);
        var storeSave = storeRepo.save(store.get());
        return Objects.isNull(storeSave);
    }

    @Override
    public boolean updateStatusStore(short id, StoreStatus status) {
        var store = storeRepo.findById(id);
        if (store.isEmpty()) {
            return false;
        }
        store.get().setStatus(status);
        var storeSave = storeRepo.save(store.get());
        return Objects.isNull(storeSave);
    }

    @Override
    public List<Store> getAll() {
        return this.storeRepo.findAll();
    }

}
