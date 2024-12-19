package com.pbl.demo.model.foodType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pbl.demo.model.foodClass.FoodClass;

public interface FoodTypeRepository extends JpaRepository<FoodType, Integer>{
    Optional<FoodClass> findByTypeID(Integer typeID);
}
