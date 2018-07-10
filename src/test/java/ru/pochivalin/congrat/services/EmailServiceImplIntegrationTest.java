package ru.pochivalin.congrat.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Store;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Properties;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@PropertySource("classpath:applicationTest.properties")
@ActiveProfiles("test")
@RunWith(SpringRunner.class)

public class EmailServiceImplIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void shouldSendSingleMail() throws InterruptedException, IOException {

        try {
            String mailFrom = ;
            String mailTo = ;
            String mailSubj = "Java";
            String mailText = "text";
            String userName = ;
            String user = ;
            String password = ;

            emailService.sendSimpleMessage(mailFrom, mailTo, mailSubj, mailText);
            Thread.sleep(5000);

            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            //Session emailSession = Session.getDefaultInstance(properties);
            Session emailSession = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    });
            //2) create the POP3 store object and connect with the pop server
            Store emailStore = emailSession.getStore("imaps");
            emailStore.connect("imap.mail.ru", user, password);

            //3) create the folder object and open it
            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            //4) retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            Message newMessage = messages[messages.length - 1];
            String subject = newMessage.getSubject();
            emailFolder.close(true);
            emailStore.close();

            assertEquals(mailSubj,newMessage.getSubject());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
