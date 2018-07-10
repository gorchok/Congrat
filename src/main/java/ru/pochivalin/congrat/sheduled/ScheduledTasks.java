package ru.pochivalin.congrat.sheduled;

import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.pochivalin.congrat.model.Person;
import ru.pochivalin.congrat.services.*;

@Component
public class ScheduledTasks {

    @Value("${number.of.events}")
    private int maxNumberOfEvent;

    @Autowired
    private PersonForBirthday personForBirthday;
    @Autowired
    private EmailService emailService;
    @Autowired
    private HtmlServiceImpl htmlService;
    @Autowired
    private ExcelServiceImpl excelService;
    @Autowired
    private PersonServiceImpl personService;
    @Autowired
    private ImageServiceImpl imageService;

    //@Scheduled(cron = "${shedule.delay}")
    public void reportCurrentTime() throws Exception {
        List<Person> listBirthday = personForBirthday.personFilter();
        int urlDay = personService.getDayBirthday(listBirthday.get(0));
        int urlMonth = personService.getMonthBirthday(listBirthday.get(0));
        String monthStr = personService.monthIntToStr(urlMonth);

        //List<String> famousEvent = htmlService.getFamousEvent("http://www.calend.ru/events", urlDay,urlMonth);
        List<String> famousEvent = htmlService.getFamousEvent("http://calendareveryday.ru/?id=12", urlDay, urlMonth);
        //List<String> famousEventCache = famousEvent;
        HashSet h = new HashSet(famousEvent);
        famousEvent.clear();
        famousEvent.addAll(h);

        for (Person pb : listBirthday) {
            String fname = pb.getFirsName();
            String lname = pb.getLastName();

            List<String> randomFamousEvent = htmlService.getRandomFamousEvent(famousEvent, maxNumberOfEvent);
            Collections.sort(randomFamousEvent);

            String born = personService.getBorn(pb.getType());

            StringBuilder sb = new StringBuilder();
            sb.append(urlDay + " " + monthStr)
              .append("\n")
              .append(randomFamousEvent.get(0))
              .append("\n")
              .append(randomFamousEvent.get(1))
              .append("\n")
              .append(randomFamousEvent.get(2))
              .append("\n")
              .append("Сегодня " + born + " ")
              .append(lname + " " + fname)
              .append(" поздравляем с Днем Рожедния ! ! !");

            List<String> emailAll = personService.getAllEmail();
            for (String e : emailAll) {
                emailService.sendSimpleMessage("gorchok@mail.ru", e, "С Днем рождения ! ! !", sb.toString());
            }
        }
    }

    //@Scheduled(cron = "${shedule.delay}")
    public void excelTask() {

        List<Person> list = personForBirthday.personForExcelBirthday();

        for (Person person : list) {
            List<Person> listWithoutPerson = personForBirthday.personForExcelMoney(person);
            int dayBirhday = personService.getDayBirthday(person);
            int mothnBirhday = personService.getMonthBirthday(person);
            String monthStr = personService.monthIntToStr(mothnBirhday);
            String born = personService.getBorn(person.getType());
            String fname = person.getFirsName();
            String lname = person.getLastName();
            String subj = dayBirhday + " " + monthStr + " " + born + " " + lname + " " + fname;
            String filename = lname + "_" + fname + ".xls";
            excelService.createTable(listWithoutPerson, subj);
            emailService.sendMessageWithAttach("gorchok@mail.ru", "gorchok@mail.ru", subj,
                    "Сбор средств", filename,
                    "E:\\Programmirovanie\\Java\\Congrat\\Congrat\\money.xls");
        }
        //System.out.println("D");

    }

    /*@Scheduled(cron = "${shedule.delay}")
    public void imageTask() {
      imageService.DownloadImage();
    }*/

}
