package com.example.usingjunitmockito.repository;

import com.example.usingjunitmockito.domain.user.User;
import com.example.usingjunitmockito.dto.UserDTO;
import com.example.usingjunitmockito.dto.UserNameDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get user name successfully from db")
    void findNameByEmailCase1() {
        String email = "name1@example.com";

        UserDTO user = new UserDTO("Name 1", email, "123");
        this.createUser(user);

        UserNameDTO userName = this.userRepository.findNameByEmail(email);

        assertThat(userName.name().equals("Name 1")).isTrue();
    }

    @Test
    @DisplayName("Should not get user name successfully from db")
    void findNameByEmailCase2() {
        String email = "name1@example.com";

        UserNameDTO userName = this.userRepository.findNameByEmail(email);

        assertThat(userName == null).isTrue();
    }

    @Test
    @DisplayName("Should get user by email from db")
    void findByEmailCase1() {
        String email = "name1@example.com";

        UserDTO user = new UserDTO("Name 1", email, "123");
        User newUser = this.createUser(user);

        User foundUser = this.userRepository.findByEmail(email);

        assertThat(foundUser.equals(newUser)).isTrue();
    }

    public User createUser(UserDTO user){
        User newUser = new User(user.name(), user.email(), user.password());
        this.entityManager.persist(newUser);

        return newUser;
    }
}