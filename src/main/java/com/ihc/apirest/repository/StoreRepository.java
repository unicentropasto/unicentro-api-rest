
package com.ihc.apirest.repository;

import com.ihc.apirest.models.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StoreRepository extends JpaRepository<Store, Long>
{

  Store findByStoreNumber(String storeNumber);
}