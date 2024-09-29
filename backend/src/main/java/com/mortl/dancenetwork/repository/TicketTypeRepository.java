package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.entity.TicketType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long>
{

  List<TicketType> findByEventId(long eventId);
}
