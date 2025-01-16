package com.pbl.demo.model.food_list;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodListRepository extends JpaRepository<FoodList, FoodList>{ 
    @Query(value = """
        SELECT userID, consumptionDate, foodID, meal, grams FROM foodList WHERE userID = :userID
    """, nativeQuery = true)
    List<FoodList> findByUserId(@Param("userID") int userID);
}