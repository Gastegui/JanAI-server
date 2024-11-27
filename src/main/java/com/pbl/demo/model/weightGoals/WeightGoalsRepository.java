package com.pbl.demo.model.weightGoals;

///import java.util.Optional;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightGoalsRepository extends JpaRepository<WeightGoals, Integer>{
    //Optional<Users> findByUsername(String username);
}
