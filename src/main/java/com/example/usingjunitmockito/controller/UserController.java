package com.example.usingjunitmockito.controller;

import com.example.usingjunitmockito.domain.user.User;
import com.example.usingjunitmockito.dto.LoginDTO;
import com.example.usingjunitmockito.dto.UserDTO;
import com.example.usingjunitmockito.dto.UserNameDTO;
import com.example.usingjunitmockito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{email}")
    public ResponseEntity<UserNameDTO> getUserName(@PathVariable String email) throws Exception {
        return new ResponseEntity<>(userService.findNameByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDTO user) throws Exception {
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO user, @PathVariable String id) throws Exception {
        return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
