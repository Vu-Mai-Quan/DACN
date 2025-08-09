package com.example.dacn.service.imlp;

import com.example.dacn.db1.model.DanhMucSanPham;
import com.example.dacn.db1.repositories.DanhMucSanPhamRepository;
import com.example.dacn.service.IParentProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service(value = "danhMucImlp")
public class DanhMucImlp implements IParentProductService<DanhMucSanPham> {
    private final DanhMucSanPhamRepository repository;

    public DanhMucImlp(final DanhMucSanPhamRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DanhMucSanPham> findAll() {
        return repository.findAll();
    }

    @Override
    public String createOrUpdate(UUID id, DanhMucSanPham danhMucSanPham) {
        danhMucSanPham.setId(id);
        repository.save(danhMucSanPham);
        return id == null ? "Thêm thành công" : "Cập nhật thành công";
    }


    @Override
    public String delete(UUID t) {
        repository.deleteById(t);
        return "Xóa thành công";
    }
}
