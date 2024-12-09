package com.pbl.demo.model.userData;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer>{
    Optional<UserData> findByUsername(String username);
}
