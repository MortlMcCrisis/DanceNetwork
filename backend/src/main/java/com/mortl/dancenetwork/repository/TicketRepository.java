package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.Ticket;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>  {

  List<Ticket> findByOwner(UUID owner);

}
