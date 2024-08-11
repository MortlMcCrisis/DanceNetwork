package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.TicketType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

  @Query("SELECT COUNT(t) = :count FROM TicketType t WHERE t.id IN :ids")
  boolean areAllIdsPresent(@Param("ids") List<Long> ids, @Param("count") long count);

  List<TicketType> findByEventId(Long eventId);
}
