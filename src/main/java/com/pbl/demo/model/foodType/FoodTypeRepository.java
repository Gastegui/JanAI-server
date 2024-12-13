package com.pbl.demo.model.foodType;

import java.util.Optional;

//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepository extends JpaRepository<FoodType, Integer>{
    Optional<FoodType> findByTypeName(String typeName);
}
