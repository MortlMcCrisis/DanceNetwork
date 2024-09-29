package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IPdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImpl implements IPdfService
{

  private static final Logger log = LoggerFactory.getLogger(PdfServiceImpl.class);

  private final TicketTypeRepository ticketTypeRepository;

  public PdfServiceImpl(TicketTypeRepository ticketTypeRepository)
  {
    this.ticketTypeRepository = ticketTypeRepository;
  }

  @Override
  public void createPdf(List<Ticket> tickets)
  {
    try
    {
      String outputPdf = "output.pdf";

      StringBuilder htmlContent = new StringBuilder();
      htmlContent.append("<html><head><style>")
          .append("body { font-family: Helvetica, Arial, sans-serif; }")
          .append(".ticket { margin: 20px; padding: 10px; border: 1px solid black; }")
          .append(".ticket h1 { font-size: 16px; }")
          .append(".ticket p { font-size: 12px; }")
          .append("</style></head><body>");

      for (int i = 0; i < tickets.size(); i++)
      {
        if (i < tickets.size() - 1)
        {
          htmlContent.append("<div style='page-break-after:always;'>");
        }
        createTicket(htmlContent, tickets.get(i));
        if (i < tickets.size() - 1)
        {
          htmlContent.append("</div>");
        }
      }

      htmlContent.append("</body></html>");

      log.info(htmlContent.toString());

      try (OutputStream os = new FileOutputStream(outputPdf))
      {
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(htmlContent.toString(), null);
        builder.toStream(os);
        builder.run();
      }

    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void createTicket(StringBuilder htmlContent, Ticket ticket)
      throws UnsupportedEncodingException
  {
    long ticketTypeId = ticket.getTicketType().getId();
    TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
        .orElseThrow(
            () -> new NoSuchElementException("Ticket type with id " + ticketTypeId + " not found"));

    String imagePath = URLEncoder.encode(
        Paths.get("../frontend/public/qrcodes/code" + ticket.getId() + ".png").toAbsolutePath()
            .toString(), StandardCharsets.UTF_8.toString()).replace("+", "%20");
    ;

    htmlContent.append("<div class='ticket'>")
        .append("<h1>").append(ticketType.getName()).append("</h1>")
        .append("<p>").append(ticketType.getDescription()).append("</p>")
        .append("<img src='file:///").append(imagePath)
        .append("' alt='QR Code' style='width:200px;height:200px;'/>")
        .append("<ul>")
        .append("<li>" + StringEscapeUtils.escapeXml10(
            ticket.getFirstName() + " " + ticket.getLastName()) + "</li>")
        .append(
            "<li>" + StringEscapeUtils.escapeXml10(ticket.getAddress() + ", " + ticket.getCountry())
                + "</li>")
        .append("<li>" + StringEscapeUtils.escapeXml10(ticket.getEmail()) + "</li>")
        .append("<li>Gender: " + ticket.getGender() + "</li>")
        .append("<li>Role: " + ticket.getDancingRole() + "</li>");
    if (ticket.getPhone() != null && !ticket.getPhone().isBlank())
    {
      htmlContent.append(
          "<li>Phone number: " + StringEscapeUtils.escapeXml10(ticket.getPhone()) + "</li>");
    }
    htmlContent.append("<li>Ticket number: " + ticket.getId() + "</li>")
        .append("</ul>")
        .append("</div>");
  }
}
