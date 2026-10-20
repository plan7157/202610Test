package com.example.demo.service;

import com.example.demo.entity.User;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    private List<User> users;

    public UserService(){
        ObjectMapper objectMapper = new ObjectMapper();
        var resource = new ClassPathResource("users.json");
        try (var in = resource.getInputStream()) {
            users = objectMapper.readValue(in, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUsers() {
        return users;
    };

    public User getUserById(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    };

    public void createUser(User user) {
        user.setId(users.getLast().getId()+1);
        users.add(user);
    }

    public boolean updateUser(Long userId,User user) {
        for (User user1 : users) {
            if (user1.getId().equals(userId)) {
                user1.setName(user.getName());
                user1.setEmail(user.getEmail());
                user1.setPhone(user.getPhone());
                user1.setUsername(user.getUsername());
                user1.setWebsite(user.getWebsite());
                return  true;
            }
        }
        return false;
    }

    public  boolean deleteUser(Long userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                users.remove(user);
                return  true;
            }
        }
        return false;
    }

}
