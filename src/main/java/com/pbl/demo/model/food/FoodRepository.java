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

    @Query("SELECT f FROM Food f " +
    "WHERE NOT EXISTS (" +
    "  SELECT 1 FROM HasIngredients hi " +
    "  JOIN hi.ingredient i " +
    "  JOIN Restrictions r ON r.ingredient = i " +
    "  WHERE hi.food = f AND r.user.userID = :userID" +
    ")")
List<Food> findFoodsExcludingUserRestrictions(@Param("userID") Long userID);
}
