package com.ihc.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihc.apirest.models.VWIdentificationType;


@Repository
public interface VWIdentificationTypeRepository extends JpaRepository<VWIdentificationType, Long>
{
  
  List<VWIdentificationType> findByGroupCode(String groupCode);
}
