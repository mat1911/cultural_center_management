package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE CONCAT(user.name, user.surname, user.username, " +
            "user.email, user.age, user.phoneNumber) LIKE %?1% ")
    Page<User> findAllAndFiltered(Pageable pageable, String keyword);
    Page<User> findAllByOrderById(Pageable pageable);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
