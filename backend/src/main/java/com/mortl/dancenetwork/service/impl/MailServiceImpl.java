package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.service.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {

  private JavaMailSender mailSender;

  public MailServiceImpl(JavaMailSender mailSender){
    this.mailSender = mailSender;
  }

  @Override
  public void sendEmail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
  }

  @Override
  public void sendEmailWithAttachment(String to, String subject, String body) {
    MimeMessage message = mailSender.createMimeMessage();

    try {
      // Helper zum Erstellen von E-Mails mit Anhängen
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body);
      helper.setFrom("noreply@dance-network.com");

      // PDF erstellen und als Anhang hinzufügen
      File pdfFile = new File("output.pdf");
      helper.addAttachment("Ticket.pdf", pdfFile);

      // E-Mail senden
      mailSender.send(message);
      System.out.println("E-Mail mit PDF-Anhang gesendet!");

    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}

