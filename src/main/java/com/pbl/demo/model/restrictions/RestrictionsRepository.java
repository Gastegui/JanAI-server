package com.pbl.demo.model.restrictions;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.ingredients.Ingredients;
@Repository
public interface RestrictionsRepository extends JpaRepository<Restrictions, Integer>{
        @Query("SELECT DISTINCT foodClass FROM Restrictions")
        /*@Query(value = "SELECT fc.classID, " +
                   "       CASE " +
                   "           WHEN ft.typeID IS NULL OR ft.typeID = 0 THEN true " +
                   "           ELSE false " +
                   "       END AS hasNullTypeID " +
                   "FROM FoodClass fc " +
                   "LEFT JOIN FoodType ft ON fc.classID = ft.classID " +
                   "GROUP BY fc.classID, hasNullTypeID", nativeQuery = true)*/
        List<FoodClass> findDistinctClasses();

        /*@Query(value = "SELECT DISTINCT IFNULL(fc.classID, 0) AS classID FROM Restrictions r LEFT JOIN FoodClass fc")
        List<Integer> findDistinctClassIDsWithDefault();*/

        @Query("SELECT DISTINCT foodType FROM Restrictions WHERE foodClass.classID = :classID")
        List<FoodType> findDistinctTypeIDsByClassID(@Param("classID") Integer classID);

        @Query("SELECT DISTINCT foodGroup FROM Restrictions WHERE foodType.typeID = :typeID")
        List<FoodGroup> findDistinctGruopIDsByTypeID(@Param("typeID") Integer typeID);
        
        @Query("SELECT DISTINCT ingredient FROM Restrictions WHERE foodGroup.groupID = :groupID")
        List<Ingredients> findDistinctIngredientIDsByGroupID(@Param("groupID") Integer groupID);
}
