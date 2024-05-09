package com.example.usingjunitmockito.service;

import com.example.usingjunitmockito.domain.user.User;
import com.example.usingjunitmockito.dto.LoginDTO;
import com.example.usingjunitmockito.dto.UserDTO;
import com.example.usingjunitmockito.dto.UserNameDTO;
import com.example.usingjunitmockito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(String id) throws Exception {
        Optional<User> user = userRepository.findById(UUID.fromString(id));

        if (user.isEmpty()) {
            throw new Exception("User not found!");
        }

        return user.get();
    }

    public UserNameDTO findNameByEmail(String email) throws Exception {
        return userRepository.findNameByEmail(email);
    }

    public User login(LoginDTO user) throws Exception {
        try {
            User userByEmail = userRepository.findByEmail(user.email());

            if (!user.password().equals(userByEmail.getPassword())) {
                throw new Exception("Failed to login!");
            }

            return userByEmail;
        } catch (Exception e) {
            throw new Exception("Failed to find user with this email");
        }
    }

    public User createUser(UserDTO user) {
        return userRepository.save(new User(user.name(), user.email(), user.password()));
    }

    public User updateUser(UserDTO user, String id) throws Exception {
        try {
            User updatedUser = findById(id);

            updatedUser.setName(user.name());
            updatedUser.setEmail(user.email());
            updatedUser.setPassword(user.password());

            return userRepository.save(updatedUser);
        } catch (Exception e) {
            throw new Exception("Failed to update user. Try again!");
        }
    }

    public void deleteUser(String id) throws Exception {
        try {
            userRepository.deleteById(UUID.fromString(id));
        } catch (Exception e) {
            throw new Exception("Failed to delete user. Try again!");
        }
    }
}
