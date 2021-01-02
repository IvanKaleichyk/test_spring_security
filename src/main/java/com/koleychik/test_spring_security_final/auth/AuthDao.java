package com.koleychik.test_spring_security_final.auth;

import com.koleychik.test_spring_security_final.models.UserModel;

import java.util.Optional;

public interface AuthDao {

    Optional<UserModel> getUserAuthByName(String name);

}
