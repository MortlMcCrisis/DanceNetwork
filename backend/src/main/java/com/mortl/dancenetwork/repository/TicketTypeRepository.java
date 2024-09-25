package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.entity.TicketType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

  @Query("SELECT COUNT(t) = :count FROM TicketType t WHERE t.id IN :ids")
  boolean areAllIdsPresent(List<Long> ids, long count);

  List<TicketType> findByEventId(long eventId);
}
