package com.ihc.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihc.apirest.models.IdentificationType;


@Repository
public interface IdentificationTypeRepository extends JpaRepository<IdentificationType, Long>
{
  
  List<IdentificationType> findByGroupCode(String groupCode);
}
