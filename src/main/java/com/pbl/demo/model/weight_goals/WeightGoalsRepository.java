package com.pbl.demo.model.weight_goals;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeightGoalsRepository extends JpaRepository<WeightGoals, Integer>{
    List<WeightGoals> findAll();

}
