package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
