package com.ihc.apirest.repository;
import com.ihc.apirest.models.Ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>
{
  @Query(value = "select " +
                "(select url_store_logo from appmall.stores s where s.store_number like '%'||(STRING_TO_ARRAY(t.desc_tip,' '))[1]||'%') as url_store_logo, " +
                "t.desc_tip as store, " +
                "sum(c.valo_com) as invoiceValue " +
                "FROM sisbol.compra c " +
                "inner join sisbol.boleta b on b.codi_bol = c.codi_bol " +
                "inner join sisbol.tipo t ON t.codi_tip = c.loca_com " +
                "where 1=1 " +
                "and b.id_camp = (select max(c.id_camp) FROM sisbol.campania c where c.estado_camp = 'C') " +
                "and b.codi_cli = ?1 " +
                "group by t.desc_tip ", nativeQuery = true)
  List<Object[]> getLastTickets(Long idCustomer);


  @Query(value = "select " +
                  "c.nombre_camp as campaign_name, " +
                  "count(db.codi_dbo) as quantity_tickets " +
                  "FROM sisbol.boleta b  " +
                  "inner join sisbol.detalle_bol db on b.codi_bol = db.codi_bol " +
                  "inner join sisbol.campania c on b.id_camp = c.id_camp " +
                  "where 1=1 " +
                  "and b.id_camp = (select max(c.id_camp) FROM sisbol.campania c where c.estado_camp = 'C') " +
                  "and b.codi_cli = ?1 " +
                  "group by c.nombre_camp", nativeQuery = true)
  List<Object[]> getQuantityTicketsByCampaign(Long idCustomer);
}