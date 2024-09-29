package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.DancingRole;
import com.mortl.dancenetwork.enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import org.springframework.data.util.Pair;

public record EventStatisticsDTO(
    @NotEmpty
    LocalDateTime eventCreationDate,
    @NotEmpty
    Map<YearMonth, Long> ticketSalesPerMonth,
    @NotEmpty
    Map<Gender, Long> genderDistribution,
    @NotEmpty
    Map<DancingRole, Long> dancingRoleDistribution,
    @NotEmpty
    List<Pair<String, Pair<Long, Long>>> ticketTypesSoldFromContingent
)
{

}
