package com.mortl.dancenetwork.service;

public interface IMailService {

  void sendEmail(String to, String subject, String body);

  void sendEmailWithAttachment(String to, String subject, String body);
}
