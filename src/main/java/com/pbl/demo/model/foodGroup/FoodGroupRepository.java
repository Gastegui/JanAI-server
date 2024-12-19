package com.pbl.demo.model.foodGroup;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pbl.demo.model.foodType.FoodType;

public interface FoodGroupRepository extends JpaRepository<FoodGroup, Integer>{
    @Query("SELECT ft FROM FoodGroup ft WHERE ft.foodType.typeID = :typeID")
    List<FoodGroup> findByClassID(@Param("typeID") Integer typeID);
}
