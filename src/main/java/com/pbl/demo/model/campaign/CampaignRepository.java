package com.pbl.demo.model.campaign;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Integer>{
    Optional<Campaign> findByCampName(String campName);
}
