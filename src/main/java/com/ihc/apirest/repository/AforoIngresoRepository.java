package com.ihc.apirest.repository;

import com.ihc.apirest.models.AforoIngreso;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface AforoIngresoRepository extends JpaRepository<AforoIngreso, Date>
{

    /**
     * Método que permite calcular el total de ingresos del dia
     */
    @Query(value = "select sum(conteo) from hechos.aforo_ingreso where fecha_ingreso >= CURRENT_DATE", nativeQuery = true)
    Long aforoClientesIngreso();
}

