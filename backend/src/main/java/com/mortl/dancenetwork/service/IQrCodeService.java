package com.mortl.dancenetwork.service;

import com.google.zxing.WriterException;

public interface IQrCodeService {

  void createQRImage(String fileName, String qrCodeText) throws WriterException;

}
