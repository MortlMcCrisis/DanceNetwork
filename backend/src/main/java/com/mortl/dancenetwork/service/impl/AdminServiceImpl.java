package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.EventStatisticsDTO;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.enums.DancingRole;
import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.repository.TicketRepository;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IAdminService;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceImpl implements IAdminService
{

  private TicketTypeRepository ticketTypeRepository;

  private TicketRepository ticketRepository;

  private EventRepository eventRepository;

  public AdminServiceImpl(
      TicketTypeRepository ticketTypeRepository,
      TicketRepository ticketRepository,
      EventRepository eventRepository)
  {
    this.ticketTypeRepository = ticketTypeRepository;
    this.ticketRepository = ticketRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public EventStatisticsDTO getEventStats(long eventId)
  {

    LocalDateTime createdAt = eventRepository.findCreatedAtByEventId(eventId);

    return new EventStatisticsDTO(
        createdAt,
        getTicketsPerMonth(createdAt, eventId),
        getTicketsByGender(eventId),
        getTicketsByDancingRole(eventId),
        getTicketsByTicketType(eventId)
    );
  }

  private Map<YearMonth, Long> getTicketsPerMonth(LocalDateTime createdAt, long eventId)
  {
    LocalDateTime now = LocalDateTime.now();

    Map<YearMonth, Long> result = new TreeMap<>();

    List<Object[]> ticketCounts = ticketRepository.countTicketsByEventIdAndMonth(eventId, createdAt,
        now);

    for (Object[] record : ticketCounts)
    {
      LocalDateTime month = (LocalDateTime) record[0];
      long count = (long) record[1];
      result.put(YearMonth.of(month.getYear(), month.getMonth()), count);
    }

    YearMonth startMonth = YearMonth.from(createdAt);
    YearMonth endMonth = YearMonth.from(now);

    for (YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1))
    {
      result.putIfAbsent(month, 0L);
    }

    return result;
  }

  private Map<Gender, Long> getTicketsByGender(long eventId)
  {
    List<Object[]> result = ticketRepository.countTicketsByGender(eventId);
    Map<Gender, Long> genderCountMap = new HashMap<>();

    for (Object[] record : result)
    {
      Gender gender = (Gender) record[0];
      long count = (long) record[1];
      genderCountMap.put(gender, count);
    }

    for (Gender gender : Gender.values())
    {
      if (!genderCountMap.containsKey(gender))
      {
        genderCountMap.put(gender, 0L);
      }
    }

    return genderCountMap;
  }

  private Map<DancingRole, Long> getTicketsByDancingRole(long eventId)
  {
    List<Object[]> result = ticketRepository.countTicketsByDancingRole(eventId);
    Map<DancingRole, Long> dancingRoleCountMap = new HashMap<>();

    for (Object[] record : result)
    {
      DancingRole dancingRole = (DancingRole) record[0];
      long count = (long) record[1];
      dancingRoleCountMap.put(dancingRole, count);
    }

    for (DancingRole dancingRole : DancingRole.values())
    {
      if (!dancingRoleCountMap.containsKey(dancingRole))
      {
        dancingRoleCountMap.put(dancingRole, 0L);
      }
    }

    return dancingRoleCountMap;
  }

  private List<Pair<String, Pair<Long, Long>>> getTicketsByTicketType(long eventId)
  {
    List<Object[]> ticketCounts = ticketRepository.countTicketsByTicketTypeId(eventId);
    List<TicketType> allTicketTypes = ticketTypeRepository.findByEventId(eventId);

    List<Pair<String, Pair<Long, Long>>> result = new ArrayList<>();

    List<TicketType> allFoundTicketTypes = new ArrayList<>();
    for (Object[] record : ticketCounts)
    {
      long ticketTypeId = ((Number) record[0]).longValue();
      long count = (long) record[1];

      TicketType foundTicketType = allTicketTypes.stream()
          .filter(ticketType -> ticketType.getId() == ticketTypeId).findAny()
          .orElseThrow(() -> new NoSuchElementException(
              "Found ticket with ticketTypeId " + ticketTypeId + " but no such ticketType"));

      String ticketTypeName = foundTicketType.getName();
      long contingent = foundTicketType.getContingent();

      result.add(Pair.of(ticketTypeName, Pair.of(count, contingent)));
      allFoundTicketTypes.add(foundTicketType);
    }

    allTicketTypes.removeAll(allFoundTicketTypes);
    allTicketTypes.stream().forEach(ticketType -> result.add(
        Pair.of(ticketType.getName(), Pair.of(0L, ticketType.getContingent()))));

    return result;
  }

}
