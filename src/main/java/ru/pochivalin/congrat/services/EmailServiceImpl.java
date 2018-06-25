package ru.pochivalin.congrat.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailServiceImpl {

    @Autowired
    public JavaMailSender emailSender;
    static final Logger emailLogger = LogManager.getLogger(EmailServiceImpl.class);

    public void sendSimpleMessage(String from,String to, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            emailLogger.info("Message send");
        }
        catch (Exception e) {
            emailLogger.error(e.getMessage());
        }
    }

    public void sendMessageWithAttachment(String from,String to, String subject, String text, String filename,String pathToAttachment) {

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
            emailLogger.info("Message send with attachment");
        }
        catch (Exception e) {
            emailLogger.error(e.getMessage());
        }
    }

}
