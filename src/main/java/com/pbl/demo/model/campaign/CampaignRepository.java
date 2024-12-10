package com.pbl.demo.model.campaign;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Integer>{
    //Optional<Campaign> findByUsername(String username);
}
