package pl.coderstrust.invoices.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.coderstrust.invoices.model.Invoice;

@Service
public class EmailService {

  private static Logger logger = LoggerFactory.getLogger(EmailService.class);

  private JavaMailSender javaMailSender;
  private MailProperties mailProperties;

  @Autowired
  public EmailService(JavaMailSender javaMailSender, MailProperties mailProperties) {
    this.javaMailSender = javaMailSender;
    this.mailProperties = mailProperties;
  }

  @Async
  public void sendEmail(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    try {
      logger.debug("Sending an email with invoice: {}", invoice);
      MimeMessage mail = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mail, true);
      helper.setTo(mailProperties.getProperties().get("receiver"));
      helper.setReplyTo(mailProperties.getUsername());
      helper.setFrom(mailProperties.getUsername());
      helper.setSubject("Accounting System - added/updated Invoice");
      helper.setText("This mail was generated, because Invoice was added or updated to database");
      javaMailSender.send(mail);
    } catch (MessagingException exception) {
      logger.error("Couldn't send mail with Invoice.", exception);
    }
  }
}