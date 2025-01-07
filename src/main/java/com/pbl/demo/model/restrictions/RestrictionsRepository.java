package com.pbl.demo.model.restrictions;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.userData.UserData;
@Repository
public interface RestrictionsRepository extends JpaRepository<Restrictions, Integer>{
        @Query("SELECT DISTINCT foodClass FROM Restrictions WHERE userData.userID = :userID")
        List<FoodClass> findDistinctClasses(@Param("userID") Integer userID);

        Optional<Restrictions> findByRestrictedName(String restrictedName);

        /*@Query(value = "SELECT DISTINCT IFNULL(fc.classID, 0) AS classID FROM Restrictions r LEFT JOIN FoodClass fc")
        List<Integer> findDistinctClassIDsWithDefault();*/

        /*@Query("SELECT DISTINCT foodType FROM Restrictions WHERE foodClass.classID = :classID and userData.userID = :userID")
        List<FoodType> findDistinctTypeIDsByClassID(@Param("classID") Integer classID, @Param("userID") Integer userID);*/

        @Query("SELECT DISTINCT r.foodType FROM Restrictions r WHERE r.foodClass.classID = :classID AND r.userData.userID = :userID")
        List<FoodType> findDistinctTypeIDsByClassID(@Param("classID") Integer classID, @Param("userID") Integer userID);

        @Query("SELECT DISTINCT foodGroup FROM Restrictions WHERE foodType.typeID = :typeID and userData.userID = :userID")
        List<FoodGroup> findDistinctGruopIDsByTypeID(@Param("typeID") Integer typeID, @Param("userID") Integer userID);
        
        @Query("SELECT DISTINCT ingredient FROM Restrictions WHERE foodGroup.groupID = :groupID and userData.userID = :userID")
        List<Ingredients> findDistinctIngredientIDsByGroupID(@Param("groupID") Integer groupID, @Param("userID") Integer userID);
}
