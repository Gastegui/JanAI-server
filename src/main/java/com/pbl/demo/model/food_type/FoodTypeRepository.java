package com.pbl.demo.model.food_type;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FoodTypeRepository extends JpaRepository<FoodType, Integer>{
    @Query("SELECT ft FROM FoodType ft WHERE ft.foodClass.classID = :classID")
    List<FoodType> findByClassID(@Param("classID") Integer classID);


    Optional<FoodType> findByTypeName(String typeName);
}
