package ru.congrat.sheduled;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.congrat.model.Person;
import ru.congrat.services.EmailServiceImpl;

@Component
public class ScheduledTasks {

    @Autowired
    PersonForBirthday personForBirthday;
    @Autowired
    EmailServiceImpl emailService;

    //private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws Exception {
        //log.info("The time is now {}", dateFormat.format(new Date()));
        System.out.println("The time is now {}: " + dateFormat.format(new Date()));
        List<Person> list = personForBirthday.personFilter();
        System.out.println(list);

        List<String> email = new ArrayList<String>();
        email.add("a.pochivalin@answerpro.org");
        email.add("gorchok@mail.ru");
        for (String e:email) {
        emailService.sendSimpleMessage("gorchok@mail.ru",e,"Поздравление","С Днем Рождения ! ! !");
        }

    }
}
