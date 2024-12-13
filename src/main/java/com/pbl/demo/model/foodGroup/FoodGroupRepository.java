package com.pbl.demo.model.foodGroup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodGroupRepository extends JpaRepository<FoodGroup, Integer>{
    Optional<FoodGroup> findByGroupName(String groupName);
}
