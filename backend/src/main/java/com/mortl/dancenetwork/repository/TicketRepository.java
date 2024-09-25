package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.enums.Role;
import com.mortl.dancenetwork.entity.Ticket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, Long>  {

  @Query("""
       SELECT ticket  
       FROM Ticket ticket 
       WHERE ticket.owner = :owner 
       ORDER BY ticket.ticketType.event.startDate ASC""")
  List<Ticket> findByOwnerOrderByEventStartDateAsc(UUID owner);

  @Query("SELECT FUNCTION('DATE_TRUNC', 'MONTH', t.buyDate) AS month, COUNT(t) AS count " +
      "FROM Ticket t WHERE t.ticketType.event.id = :eventId " +
      "AND t.buyDate >= :startDate AND t.buyDate < :endDate " +
      "GROUP BY FUNCTION('DATE_TRUNC', 'MONTH', t.buyDate) " +
      "ORDER BY FUNCTION('DATE_TRUNC', 'MONTH', t.buyDate) ASC")
  List<Object[]> countTicketsByEventIdAndMonth(long eventId, LocalDateTime startDate, LocalDateTime endDate);

  @Query("SELECT t.gender, COUNT(t) FROM Ticket t WHERE t.ticketType.event.id = :eventId GROUP BY t.gender")
  List<Object[]> countTicketsByGender(long eventId);

  @Query("SELECT t.role, COUNT(t) FROM Ticket t WHERE t.ticketType.event.id = :eventId GROUP BY t.role")
  List<Object[]> countTicketsByRole(long eventId);

  @Query("SELECT t.ticketType.id, COUNT(t) AS count " +
      "FROM Ticket t WHERE t.ticketType.event.id = :eventId " +
      "GROUP BY t.ticketType.id")
  List<Object[]> countTicketsByTicketTypeId(long eventId);
}
