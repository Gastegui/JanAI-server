package com.pbl.demo.model.users;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>{
    Optional<Users> findByUsername(String username);
}
