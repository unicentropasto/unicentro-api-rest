package com.ihc.apirest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihc.apirest.models.VWNeighborhood;


@Repository
public interface VWNeighborhoodRepository extends JpaRepository<VWNeighborhood, Long>
{

}
