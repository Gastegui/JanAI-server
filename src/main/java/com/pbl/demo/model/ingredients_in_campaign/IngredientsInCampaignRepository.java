package com.pbl.demo.model.ingredients_in_campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsInCampaignRepository extends JpaRepository<IngredientsInCampaign, IngredientsInCampaign>{

    @Query("SELECT COUNT(ic) > 0 FROM IngredientsInCampaign ic WHERE ic.campaign.campaignID = :campaignID AND ic.ingredients.ingredientID = :ingredientID")
    boolean findIngredientsInCampaignByCampaignIDAndIngredientID(@Param("campaignID") Integer campaignID, @Param("ingredientID") Integer ingredientID);

}
