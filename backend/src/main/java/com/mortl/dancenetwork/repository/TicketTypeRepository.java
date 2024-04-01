package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.TicketType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

  List<TicketType> findByEventId(Long eventId);
}
