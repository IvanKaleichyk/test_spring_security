package com.koleychik.test_spring_security_final.controllers;

import com.koleychik.test_spring_security_final.models.UserModel;
import com.koleychik.test_spring_security_final.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public List<UserModel> getAllInfo() {
        return repository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users:post')")
    public UserModel insert(@RequestBody UserModel model) {
        return repository.save(model);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public UserModel getById(@PathVariable long id) {
        return repository.findById(id).orElseThrow(() -> new NullPointerException("cannot find object with id = " + id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('users:update')")
    public void updateName(@PathVariable long id, @RequestParam String name) {
        repository.updateName(id, name);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('users:update')")
    public void updateEmail(@PathVariable long id, @RequestParam String email) {
        repository.updateName(id, email);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:delete')")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}
