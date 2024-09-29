package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.DancingRole;
import com.mortl.dancenetwork.enums.Gender;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import org.springframework.data.util.Pair;

public record EventStatisticsDTO(
    LocalDateTime eventCreationDate,
    Map<YearMonth, Long> ticketSalesPerMonth,
    Map<Gender, Long> genderDistribution,
    Map<DancingRole, Long> dancingRoleDistribution,
    List<Pair<String, Pair<Long, Long>>> ticketTypesSoldFromContingent
)
{

}
