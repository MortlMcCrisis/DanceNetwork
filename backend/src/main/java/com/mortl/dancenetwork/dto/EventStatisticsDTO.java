package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.enums.Role;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import org.springframework.data.util.Pair;

public record EventStatisticsDTO(
  LocalDateTime eventCreationDate,
  Map<YearMonth, Long> ticketSalesPerMonth,
  Map<Gender, Long> genderDistribution,
  Map<Role, Long> roleDistribution,
  List<Pair<String, Pair<Long, Long>>> ticketTypesSoldFromContingent
){
}
