package com.pbl.demo.model.food;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FoodRepository extends JpaRepository<Food, Integer>{
    Optional<Food> findByFoodName(String foodName);
}
