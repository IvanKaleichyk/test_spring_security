package com.koleychik.test_spring_security_final.repositories;

import com.koleychik.test_spring_security_final.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

    @Query("UPDATE UserModel u SET u.name = :name WHERE u.id = :id")
    void updateName(long id, String name);

    @Query("UPDATE UserModel u SET u.email = :email WHERE u.id = :id")
    void updateEmail(long id, String email);
}
