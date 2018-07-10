package ru.pochivalin.congrat.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Log4j2
@Component
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    //private static final Logger EMAILLOG = LogManager.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleMessage(final String from, final String to,
                                  final String subject, final String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            log.info("Message send");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendMessageWithAttach(final String from, final String to,
                                      final String subject, final String text,
                                      final String filename, final String pathToAttachment) {

        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment(filename, file);

            emailSender.send(message);
            log.info("Message send with attachment");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
