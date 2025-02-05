package com.pbl.demo.model.administrator;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
    Optional<Administrator> findByUsername(String username);
}
