package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.PaymentRequestDTO;

public interface IPaymentService {

  void doPayment(PaymentRequestDTO paymentRequestDTO);

}
