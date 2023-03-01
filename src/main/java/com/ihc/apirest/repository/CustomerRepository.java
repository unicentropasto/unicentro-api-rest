package com.ihc.apirest.repository;

import com.ihc.apirest.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    
    @Modifying
    @Query("update Customer c SET c.password = ?1 where c.idCustomer = ?2")
    Integer updatePasswordCustomer(String password, Long idCustomer);


    @Modifying
    @Query("update Customer c SET c.password= ?1 where c.email = ?2")
    Integer restorePassword(String password, String email);


    Customer findByEmail(String email);


    Customer findByIdentificationDocument(String identificationDocument);
    

    boolean existsByEmail(String email);
}