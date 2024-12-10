package com.pbl.demo.model.ingredientsInCampaign;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsInCampaignRepository extends JpaRepository<IngredientsInCampaign, Integer>{
    //Optional<Campaign> findByUsername(String username);
}
