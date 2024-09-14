package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.entity.Ticket;
import java.util.List;

public interface IPdfService {

  void createPdf(List<Ticket> tickets);
}
