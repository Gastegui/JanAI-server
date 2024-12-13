package com.pbl.demo.model.ingredientsInCampaign;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsInCampaignRepository extends JpaRepository<IngredientsInCampaign, IngredientsInCampaignPK>{
    //Optional<Campaign> findByUsername(String username);
}
