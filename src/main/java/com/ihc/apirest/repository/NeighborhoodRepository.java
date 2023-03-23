package com.ihc.apirest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihc.apirest.models.Neighborhood;


@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long>
{

}
