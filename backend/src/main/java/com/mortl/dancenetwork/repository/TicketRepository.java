package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.Ticket;
import java.util.List;
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
}
