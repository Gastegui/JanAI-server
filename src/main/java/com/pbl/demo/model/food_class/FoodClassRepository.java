package com.pbl.demo.model.food_class;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodClassRepository extends JpaRepository<FoodClass, Integer>{
    Optional<FoodClass> findByClassID(Integer classID);
    Optional<FoodClass> findByClassName(String className);
}
