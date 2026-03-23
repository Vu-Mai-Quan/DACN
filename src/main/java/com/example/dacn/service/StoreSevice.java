/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.db1.model.Store;
import com.example.dacn.template.enumModel.StoreStatus;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface StoreSevice {

    boolean createStore(Store store);

    boolean renameStore(String name, final short id);

    boolean updateStatusStore(final short id, StoreStatus status);
    
    List<Store> getAll();
}
