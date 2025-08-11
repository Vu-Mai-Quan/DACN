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

}
