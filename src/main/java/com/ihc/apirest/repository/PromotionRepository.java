package com.ihc.apirest.repository;

import com.ihc.apirest.models.Promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>
{
  
}