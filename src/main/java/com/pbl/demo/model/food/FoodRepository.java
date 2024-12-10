package com.pbl.demo.model.food;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodRepository extends JpaRepository<Food, Integer>{
    Optional<Food> findByFoodName(String foodName);
    
    @Query("SELECT f FROM Food f JOIN f.ingredients hi JOIN hi.ingredient i WHERE i.ingName IN :ingredientNames")
    List<Food> findFoodsByIngredients(@Param("ingredientNames") List<String> ingredientNames);
}
