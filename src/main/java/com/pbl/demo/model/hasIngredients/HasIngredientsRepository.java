package com.pbl.demo.model.hasIngredients;

//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HasIngredientsRepository extends JpaRepository<HasIngredients, Integer>{
    //Optional<FoodClass> findByUsername(String username);
}
