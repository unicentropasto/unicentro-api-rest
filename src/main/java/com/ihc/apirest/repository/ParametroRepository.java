package com.ihc.apirest.repository;

import com.ihc.apirest.models.Parametro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long>
{

}

