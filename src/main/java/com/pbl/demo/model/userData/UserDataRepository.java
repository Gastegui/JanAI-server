package com.pbl.demo.model.userData;

import java.util.Optional;
import java.util.List;
import java.util.Map;

import org.hibernate.sql.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pbl.demo.model.restrictions.Restrictions;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    Optional<UserData> findByUsername(String username);

    Optional<UserData> findByEmail(String email);

    Optional<UserData> findById(Integer userID);

    List<UserData> findAll();

    @Query(value = "SELECT u.userID AS userID, r.restrictionID AS restrictionID, r.restrictedName AS restrictedName, r.groupID AS groupID, r.typeID AS typeID, r.classID as classID, r.ingredientID as ingredientID FROM userData u JOIN restrictions r ON u.userID = r.userID", nativeQuery = true)
    List<Map<String, Object>> findUsersWithRestrictionsNative();
}
