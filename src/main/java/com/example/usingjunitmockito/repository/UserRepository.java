package com.example.usingjunitmockito.repository;

import com.example.usingjunitmockito.domain.user.User;
import com.example.usingjunitmockito.dto.UserNameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Query("SELECT new com.example.usingjunitmockito.dto.UserNameDTO(u.name) FROM users u WHERE u.email = :email")
    UserNameDTO findNameByEmail(String email);
}
