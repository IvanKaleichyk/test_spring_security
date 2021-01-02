package com.koleychik.test_spring_security_final.repositories;

import com.koleychik.test_spring_security_final.models.InfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InfoRepository extends JpaRepository<InfoModel, Long> {
    Optional<InfoModel> findById(long id);

    @Query("UPDATE InfoModel u SET u.name =:name WHERE u.id =:id")
    void updateName(long id, String name);

}
