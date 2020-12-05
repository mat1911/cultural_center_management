package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.MyRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<MyRole, Long> {
}
