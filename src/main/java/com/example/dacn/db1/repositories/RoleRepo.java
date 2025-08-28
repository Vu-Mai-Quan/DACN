package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.Role;
import com.example.dacn.enumvalues.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Set<Role> findAllByRole(EnumRole role);

    @Query("select r from Role r where r.role in (:role)")
    Set<Role> findAllByRoleNames(@Param("role") Set<EnumRole> role);

//    @Query(value = """
//             update dacn_repair_service_booking_system.tai_khoan_va_chuc_vu tk_cv set\s
//             tk_cv.id_role = (select r.id from role r where r.role_name = ?1)\s
//             where tk_cv.id_tai_khoan in (?2);
//            \s""", nativeQuery = true)
//    @Modifying
//    void updateTaiKhoanByRole(EnumRole roleName, Set<UUID> idTaiKhoan);
//
//    @Query(value = "delete from tai_khoan_va_chuc_vu tk_cv where id_tai_khoan in (?1);", nativeQuery = true)
//    void deleteRoleBeforeUpdateRole(Set<UUID> idTaiKhoan);

    @Query(value = "select (case when (count(r) > 0) then true else false end) from Role r join r.taiKhoans tk where tk.id in (?1) and r.role = com.example.dacn.enumvalues.EnumRole.ADMIN")
    boolean kiemTraDSIdCoNguoiDungAdmin(Set<UUID> idTaiKhoan);

}
