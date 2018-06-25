package ru.pochivalin.congrat.sheduled;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.pochivalin.congrat.model.Person;
import ru.pochivalin.congrat.services.EmailServiceImpl;
import ru.pochivalin.congrat.services.ExcelServiceImpl;
import ru.pochivalin.congrat.services.HtmlServiceImpl;
import ru.pochivalin.congrat.services.PersonServiceImpl;

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
    @Autowired
    PersonServiceImpl personService;

    //private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /*@Scheduled(cron = "${shedule.delay}")
    public void reportCurrentTime() throws Exception {
        //log.info("The time is now {}", dateFormat.format(new Date()));
        System.out.println("The time is now {}: " + dateFormat.format(new Date()));
        List<Person> listBirthday = personForBirthday.personFilter();
        int urlDay = personService.getDayBirthday(listBirthday.get(0));
        int urlMonth = personService.getMonthBirthday(listBirthday.get(0));
        String monthStr = personService.monthIntToStr(urlMonth);
        //System.out.println(list);

        //excelService.CreateTable(list);

        //List<String> famousEvent = htmlService.getFamousEvent("http://www.calend.ru/events", urlDay,urlMonth);
        List<String> famousEvent = htmlService.getFamousEvent("http://calendareveryday.ru/?id=12", urlDay,urlMonth);
        //List<String> famousEventCache = famousEvent;

        HashSet h = new HashSet(famousEvent);
        famousEvent.clear();
        famousEvent.addAll(h);

        for (Person pb : listBirthday) {
            String fname = pb.getFirsName();
            String lname = pb.getLastName();

            List<String> randomFamousEvent = htmlService.getRandomFamousEvent(famousEvent, 3);
            Collections.sort(randomFamousEvent);

            String born = personService.getBorn(pb.getType());

            StringBuilder sb = new StringBuilder();
            sb.append(urlDay + " " + monthStr)
              .append("\n")
              .append(" - "+randomFamousEvent.get(0))
              .append("\n")
              .append(" - "+randomFamousEvent.get(1))
              .append("\n")
              .append(" - "+randomFamousEvent.get(2))
              .append("\n")
              .append("Сегодня " + born + " ")
              .append(fname + " " + lname)
              .append(" поздравляем с Днем Рожедния ! ! !");

            List<String> emailAll = personService.getAllEmail();
            for (String e : emailAll) {
                emailService.sendSimpleMessage("gorchok@mail.ru", e, "С Днем рождения ! ! !", sb.toString());
            }
        }
    }*/

    @Scheduled(cron = "${shedule.delay}")
    public void excelTask() {

        List<Person> list = personForBirthday.personForExcelBirthday();

        for(Person person : list) {
            List<Person> listWithoutPerson = personForBirthday.personForExcelMoney(person);
            int dayBirhday = personService.getDayBirthday(person);
            int mothnBirhday = personService.getMonthBirthday(person);
            String monthStr = personService.monthIntToStr(mothnBirhday);
            String born = personService.getBorn(person.getType());
            String fname = person.getFirsName();
            String lname = person.getLastName();
            String subj = dayBirhday + " " + monthStr + " " + born + " " + lname + " " + fname;
            String filename = lname + "_" + fname + ".xls";
            excelService.CreateTable(listWithoutPerson,subj);
            emailService.sendMessageWithAttachment("gorchok@mail.ru","gorchok@mail.ru",subj, "Сбор средств", filename,"money.xls");
        }
        System.out.println("D");

    }

}
