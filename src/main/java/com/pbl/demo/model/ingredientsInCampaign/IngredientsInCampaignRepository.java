package com.pbl.demo.model.ingredientsInCampaign;

import java.util.Optional;
import java.util.List;

import org.hibernate.action.internal.OrphanRemovalAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbl.demo.model.foodType.FoodType;
@Repository
public interface IngredientsInCampaignRepository extends JpaRepository<IngredientsInCampaign, IngredientsInCampaign>{
    //Optional<Campaign> findByUsername(String username);

    @Query("SELECT COUNT(ic) > 0 FROM IngredientsInCampaign ic WHERE ic.campaign.campaignID = :campaignID AND ic.ingredients.ingredientID = :ingredientID")
    boolean findIngredientsInCampaignByCampaignIDAndIngredientID(@Param("campaignID") Integer campaignID, @Param("ingredientID") Integer ingredientID);

}
