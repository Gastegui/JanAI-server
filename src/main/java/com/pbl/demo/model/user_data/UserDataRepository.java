package com.pbl.demo.model.user_data;

import java.util.Optional;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    Optional<UserData> findByUsername(String username);

    Optional<UserData> findByEmail(String email);

    Optional<UserData> findById(Integer userID);

    List<UserData> findAll();

    @Query(value = "SELECT u.userID AS userID, r.restrictionID AS restrictionID, r.groupID AS groupID, r.typeID AS typeID, r.classID as classID, r.ingredientID as ingredientID FROM userData u JOIN restrictions r ON u.userID = r.userID", nativeQuery = true)
    List<Map<String, Object>> findUsersWithRestrictionsNative();

    @Query("SELECT u.finalDailyCalorieIntake FROM UserData u WHERE u.username = :username")
    Optional<Float> findFinalDailyCalorieIntakeByUsername(@Param("username") String username);
    
    @Query("SELECT u.waterIntake FROM UserData u WHERE u.userID = :userID")
    Optional<Float> findWaterIntakeByUserID(@Param("userID") int userID);
    
    @Query("SELECT u.waterCounter FROM UserData u WHERE u.username = :username")
    Optional<Integer> findWaterCounterByUsername(@Param("username") String username);
}
