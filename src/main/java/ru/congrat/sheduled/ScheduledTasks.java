package ru.congrat.sheduled;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.congrat.model.Person;
import ru.congrat.services.EmailServiceImpl;
import ru.congrat.services.ExcelServiceImpl;
import ru.congrat.services.HtmlServiceImpl;

@Component
public class ScheduledTasks {

    @Autowired
    PersonForBirthday personForBirthday;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    HtmlServiceImpl htmlService;
    @Autowired
    ExcelServiceImpl excelService;

    //private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws Exception {
        //log.info("The time is now {}", dateFormat.format(new Date()));
        System.out.println("The time is now {}: " + dateFormat.format(new Date()));
        List<Person> list = personForBirthday.personFilter();
        System.out.println(list);

        excelService.CreateTable(list);

        List<String> famousEvent = htmlService.getFamousEvent("http://www.calend.ru/events", 18, 6, "a");
        List<String> randomFamousEvent = htmlService.getRandomFamousEvent(famousEvent,3);
        List<String> email = new ArrayList<String>();
        email.add("a.pochivalin@answerpro.org");
        email.add("gorchok@mail.ru");
        for (String e:email) {
        emailService.sendSimpleMessage("gorchok@mail.ru",e,"Поздравление","С Днем Рождения ! ! !");
        }

    }
}
