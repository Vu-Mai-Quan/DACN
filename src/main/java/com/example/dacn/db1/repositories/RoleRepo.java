package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.Role;
import com.example.dacn.enumvalues.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Set<Role> findAllByRole(EnumRole role);
}
