package com.pbl.demo.model.food_list;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodListRepository extends JpaRepository<FoodList, FoodList>{ 
    @Query(value = """
        SELECT userID, consumptionDate, foodID, meal FROM foodList WHERE userID = :userID
    """, nativeQuery = true)
    List<FoodList> findByUserId(@Param("userID") int userID);
}
/* 
        SELECT 
            SUM(f.carbs) as sumCarbs, 
            SUM(f.proteins) as sumProteins, 
            SUM(f.fats) as sumFats, 
            SUM(f.fiber) as sumFibers, 
            SUM(f.calories) as sumCalories
        FROM foodlist fl 
        JOIN food f ON fl.foodID = f.foodID 
        WHERE fl.userID = :userID
        GROUP BY f.foodID
 */
