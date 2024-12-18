package com.pbl.demo.model.hasIngredients; //FIX JpaRepository

//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HasIngredientsRepository extends JpaRepository<HasIngredients, HasIngredients>{
    //Optional<FoodClass> findByUsername(String username);
}
