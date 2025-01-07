package com.pbl.demo.model.ingredients;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.restrictions.Restrictions;

public interface IngredientsRepository extends JpaRepository<Ingredients, Integer>{
    Optional<Ingredients> findByIngName(String ingName);
    Optional<Ingredients> findByRestrictions(Restrictions restrictions);


    @Query("SELECT ft FROM Ingredients ft WHERE ft.groupID.groupID = :groupID")
    List<Ingredients> findByGroupID(@Param("groupID") Integer groupID);
    
}
