package com.pbl.demo.model.ingredients;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pbl.demo.model.restrictions.Restrictions;

public interface IngredientsRepository extends JpaRepository<Ingredients, Integer>{
    Optional<Ingredients> findByIngName(String ingName);
    Optional<Ingredients> findByRestrictions(Restrictions restrictions);
}
