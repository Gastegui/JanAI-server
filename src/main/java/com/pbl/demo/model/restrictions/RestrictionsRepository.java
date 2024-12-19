package com.pbl.demo.model.restrictions;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodType.FoodType;
@Repository
public interface RestrictionsRepository extends JpaRepository<Restrictions, Integer>{
        @Query("SELECT DISTINCT foodClass FROM Restrictions")
        List<FoodClass> findDistinctClasses();

        @Query("SELECT DISTINCT foodType FROM Restrictions WHERE foodClass.classID = :classID")
        List<FoodType> findDistinctTypeIDsByClassID(@Param("classID") Integer classID);
}
