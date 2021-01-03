package com.koleychik.test_spring_security_final.controllers;

import com.koleychik.test_spring_security_final.models.InfoModel;
import com.koleychik.test_spring_security_final.repositories.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/info")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class InfoController {

    private final InfoRepository repository;

    @Autowired
    public InfoController(InfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('info:read')")
    public List<InfoModel> getAllInfo() {
        return repository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('info:post')")
    public InfoModel insert(@RequestBody InfoModel model) {
        return repository.save(model);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('info:read')")
    public InfoModel getById(@PathVariable long id) {
        return repository.findById(id).orElseThrow(() -> new NullPointerException("cannot find object with id = " + id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('info:update')")
    public void updateName(@PathVariable long id, @RequestParam String name) {
        repository.updateName(id, name);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('info:delete')")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}
