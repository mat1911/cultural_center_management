package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
