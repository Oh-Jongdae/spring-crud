package com.springcrud.controller;

import com.springcrud.model.UserModel;
import com.springcrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserModel> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable(value = "id") int id) throws Exception {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id :" + id));
    }

    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return this.userRepository.save(user);
    }

    @PutMapping("/{id}")
    public UserModel updateUser(@RequestBody UserModel user, @PathVariable("id") int id) throws Exception {
        UserModel existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id :" + id));
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserModel> deleteUser(@PathVariable("id") int id) throws Exception {
        UserModel existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id :" + id));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
