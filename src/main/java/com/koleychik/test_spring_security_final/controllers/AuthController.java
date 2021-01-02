package com.koleychik.test_spring_security_final.controllers;

import com.koleychik.test_spring_security_final.jwt.JwtUtils;
import com.koleychik.test_spring_security_final.models.AuthRequest;
import com.koleychik.test_spring_security_final.models.UserModel;
import com.koleychik.test_spring_security_final.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserRepository repository, JwtUtils jwtUtils) {
        this.repository = repository;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/signIn")
    public String login(@RequestBody AuthRequest request) {
        Optional<UserModel> optional = repository.findByEmail(request.getEmail());
        if (optional.isEmpty()) return "cannot find user";
        UserModel model = optional.get();
        return jwtUtils.createToken(model.getEmail(), model.getAuthorities());
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserModel model) {
        repository.save(model);
        return jwtUtils.createToken(model.getEmail(), model.getAuthorities());
    }

}
