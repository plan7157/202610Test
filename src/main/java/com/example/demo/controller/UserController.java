package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private final Validator validator;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<User> getUsers(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity creatUser(@RequestBody User user) {
        user.setId(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("error", String.valueOf(violations));
            return ResponseEntity.badRequest().body(errors);
        }
        userService.createUser(user);
        return ResponseEntity.created(null).build();
    }

    @PutMapping(value = "/users/{userId}")
    public ResponseEntity updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setId(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("error", String.valueOf(violations));
            return ResponseEntity.badRequest().body(errors);
        }
        boolean updateStatus = userService.updateUser(userId, user);
        if (!updateStatus) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok().build();
        }
    }
    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        boolean deleteStatus = userService.deleteUser(userId);
        if (!deleteStatus) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok().build();
        }
    }
}
