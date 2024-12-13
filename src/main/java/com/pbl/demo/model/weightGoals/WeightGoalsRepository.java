package com.pbl.demo.model.weightGoals;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

//import com.pbl.demo.model.userData.UserData;

public interface WeightGoalsRepository extends JpaRepository<WeightGoals, Integer>{
    //List<WeightGoals> findByUserData(UserData user);
}
