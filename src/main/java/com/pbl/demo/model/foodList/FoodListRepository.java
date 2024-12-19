package com.pbl.demo.model.foodList;

//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodListRepository extends JpaRepository<FoodList, FoodList>{ 
    //Optional<FoodClass> findByUsername(String username);
}
