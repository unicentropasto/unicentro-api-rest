package com.ihc.apirest.repository;

import com.ihc.apirest.models.ConfigurationImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ConfigurationImageRepository extends JpaRepository<ConfigurationImage, Long>
{

  List<ConfigurationImage> findByType(String type); 
}