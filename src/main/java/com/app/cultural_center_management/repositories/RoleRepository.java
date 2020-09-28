package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.MyRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<MyRole, Long> {
}
