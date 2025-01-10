package com.pbl.demo.model.foodGroup;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FoodGroupRepository extends JpaRepository<FoodGroup, Integer>{
    @Query("SELECT ft FROM FoodGroup ft WHERE ft.foodType.typeID = :typeID")
    List<FoodGroup> findByTypeID(@Param("typeID") Integer typeID);

    Optional<FoodGroup> findByGroupID(int groupID);
    Optional<FoodGroup> findByGroupName(String groupName);
}
