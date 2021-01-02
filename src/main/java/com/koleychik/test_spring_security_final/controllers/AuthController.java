package com.koleychik.test_spring_security_final.controllers;

import com.koleychik.test_spring_security_final.models.AuthRequest;
import com.koleychik.test_spring_security_final.models.UserModel;
import com.koleychik.test_spring_security_final.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;

    @Autowired
    public AuthController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/signIn")
    public String login(@RequestBody AuthRequest request) {
        // TODO -> CHECK USER
        // TODO -> RETURN TOKEN
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserModel model) {
        repository.save(model);
        // TODO -> RETURN TOKEN
    }

}
