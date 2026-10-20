package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class User {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    private String phone;
    private String website;
}
