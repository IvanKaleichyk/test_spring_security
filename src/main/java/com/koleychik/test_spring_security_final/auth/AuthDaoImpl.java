package com.koleychik.test_spring_security_final.auth;

import com.koleychik.test_spring_security_final.models.UserModel;
import com.koleychik.test_spring_security_final.repositories.UserRepository;
import com.koleychik.test_spring_security_final.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("authDaoImpl")
public class AuthDaoImpl implements AuthDao {

//    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

//    @Autowired
//    public AuthDaoImpl(PasswordEncoder passwordEncoder, UserRepository repository) {
//        this.passwordEncoder = passwordEncoder;
//        this.repository = repository;
//    }

    @Autowired
    public AuthDaoImpl( UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserModel> getUserAuthByName(String name) {
        return repository.findByEmail(name);
//        return getUsersAuth().stream().filter(it -> it.e().equals(name)).findFirst();
    }

//    private List<UserAuth> getUsersAuth() {
//        List<UserAuth> list = new ArrayList<>();
//        list.add(new UserAuth(
//                        "Evan",
//                        passwordEncoder.encode("testPassword"),
//                        true,
//                        true,
//                        true,
//                        true,
//                        Role.ADMIN.getSimpleGrantedAuthorities()
//                )
//        );
//        list.add(new UserAuth(
//                "TEstUser",
//                passwordEncoder.encode("testPassword"),
//                true,
//                true,
//                true,
//                true,
//                Role.ADMIN.getSimpleGrantedAuthorities()
//        ));
//        return list;
//    }

}
