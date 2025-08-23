package com.example.dacn.service.impl;

import com.example.dacn.db1.model.HangSanXuat;
import com.example.dacn.db1.repositories.HangSanXuatRepository;
import com.example.dacn.service.IParentProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(value = "nhaCungCapImlp")
@RequiredArgsConstructor
public class NhaCungCapImlp implements IParentProductService<HangSanXuat> {

    private final HangSanXuatRepository repository;

    @Override
    public List<HangSanXuat> findAll() {
        return repository.findAll();
    }

    @Override
    public String createOrUpdate(UUID id, HangSanXuat hangSanXuat) {
        hangSanXuat.setId(id);
        repository.save(hangSanXuat);
        return id == null ? "Thêm thành công" : "Cập nhật thành công";
    }


    @Override
    public String delete(UUID t) {
        repository.deleteById(t);
        return "Xóa nhà cung cấp id: %s".formatted(t.toString());
    }
}
