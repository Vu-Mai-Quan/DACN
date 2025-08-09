package com.example.dacn.service;

import java.util.List;
import java.util.UUID;

public interface IParentProductService <T>{
    List<T> findAll();
    String createOrUpdate(UUID id,T t);
    String delete(UUID t);


}
