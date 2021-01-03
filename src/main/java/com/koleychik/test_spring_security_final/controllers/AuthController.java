package com.koleychik.test_spring_security_final.controllers;

import com.koleychik.test_spring_security_final.jwt.JwtUtils;
import com.koleychik.test_spring_security_final.models.AuthRequest;
import com.koleychik.test_spring_security_final.models.UserModel;
import com.koleychik.test_spring_security_final.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository repository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signIn")
    public String login(@RequestBody AuthRequest request) {
        Optional<UserModel> optional = repository.findByEmail(request.getEmail());
        if (optional.isEmpty()) return "cannot find user";
        UserModel model = optional.get();
        return jwtUtils.createToken(model.getEmail(), model.getAuthorities());
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserModel model) {
        repository.save(model);
        model.setPassword(passwordEncoder.encode(model.getPassword()));
        return jwtUtils.createToken(model.getEmail(), model.getAuthorities());
    }

//    @PostMapping("/signUp")
//    public String signUp(){
//        return "All is ok";
//    }

}
