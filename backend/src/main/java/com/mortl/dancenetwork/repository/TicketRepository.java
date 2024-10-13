package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.entity.Ticket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, Long>
{

  @Query("""
      SELECT ticket  
      FROM Ticket ticket 
      WHERE ticket.owner = :owner 
      ORDER BY ticket.ticketType.event.startDate ASC""")
  List<Ticket> findByOwnerOrderByEventStartDateAsc(UUID owner);

  @Query(
      "SELECT FUNCTION('DATE_TRUNC', 'MONTH', t.ticketOrder.buyDate) AS month, COUNT(t) AS count " +
          "FROM Ticket t WHERE t.ticketType.event.id = :eventId " +
          "AND t.ticketOrder.buyDate >= :startDate AND t.ticketOrder.buyDate < :endDate " +
          "GROUP BY FUNCTION('DATE_TRUNC', 'MONTH', t.ticketOrder.buyDate) " +
          "ORDER BY FUNCTION('DATE_TRUNC', 'MONTH', t.ticketOrder.buyDate) ASC")
  List<Object[]> countTicketsByEventIdAndMonth(long eventId, LocalDateTime startDate,
      LocalDateTime endDate);

  @Query("SELECT t.gender, COUNT(t) FROM Ticket t WHERE t.ticketType.event.id = :eventId GROUP BY t.gender")
  List<Object[]> countTicketsByGender(long eventId);

  @Query("SELECT t.dancingRole, COUNT(t) FROM Ticket t WHERE t.ticketType.event.id = :eventId GROUP BY t.dancingRole")
  List<Object[]> countTicketsByDancingRole(long eventId);

  @Query("SELECT t.ticketType.id, COUNT(t) AS count " +
      "FROM Ticket t WHERE t.ticketType.event.id = :eventId " +
      "GROUP BY t.ticketType.id")
  List<Object[]> countTicketsByTicketTypeId(long eventId);

  @Query("""
      SELECT ticket  
      FROM Ticket ticket 
      WHERE ticket.ticketType.event.id = :eventId""")
  List<Ticket> findByEventId(long eventId);

  //TODO write test
  @Query("""
      SELECT ticket  
      FROM Ticket ticket 
      WHERE ticket.ticketOrder.id = :orderId""")
  List<Ticket> findByOrderId(long orderId);
}
