package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.EventStatisticsDTO;

public interface IAdminService {

  EventStatisticsDTO getEventStats(long eventId);

}
