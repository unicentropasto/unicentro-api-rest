package com.ihc.apirest.repository;

import com.ihc.apirest.models.AforoSalida;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface AforoSalidaRepository extends JpaRepository<AforoSalida, Date>
{

    /**
     * Método que permite calcular el total de salidas del dia
     */
    @Query(value = "select sum(conteo) from hechos.aforo_salida where fecha_salida >= CURRENT_DATE", nativeQuery = true)
    Long aforoClientesSalida();
}

