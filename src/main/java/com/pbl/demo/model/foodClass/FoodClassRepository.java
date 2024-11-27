package com.pbl.demo.model.foodClass;

//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodClassRepository extends JpaRepository<FoodClass, Integer>{
    //Optional<FoodClass> findByUsername(String username);
}
