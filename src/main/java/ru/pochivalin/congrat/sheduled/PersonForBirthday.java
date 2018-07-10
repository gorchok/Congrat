package ru.pochivalin.congrat.sheduled;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pochivalin.congrat.model.Person;
import ru.pochivalin.congrat.services.PersonService;

@Component
public class PersonForBirthday {

    @Value("${day.delay}")
    private int dayDelay;

    @Autowired
    private PersonService personService;

    public List<Person> personFilter() {
        List<Person> personListBirthday = new ArrayList<Person>();
        try {
            List<Person> listPerson = personService.findAll();
            Calendar currentDate = Calendar.getInstance();
            //System.out.println("call url " + currentDate.getTime());
            int month = currentDate.get(Calendar.MONTH) + 1;
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            //System.out.println(day + " " + month);

            for (Person p : listPerson) {
                //Calendar pDate = Calendar.getInstance();
                //pDate.setTime(p.getDate());
                //int pMonth = pDate.get(Calendar.MONTH) + 1;
                int pMonth = personService.getMonthBirthday(p);
                int pDay = personService.getDayBirthday(p);
                //int pDay = pDate.get(Calendar.DAY_OF_MONTH);
                if (month == pMonth && day == pDay) {
                    personListBirthday.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personListBirthday;
    }

    public List<Person> personForExcelBirthday() {
        List<Person> personListBirthday = new ArrayList<Person>();
        try {
            List<Person> listPerson = personService.findAll();
            Calendar currentDate = Calendar.getInstance();
            int month = currentDate.get(Calendar.MONTH) + 1;
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            for (Person p : listPerson) {
                int pMonth = personService.getMonthBirthday(p);
                int pDay = personService.getDayBirthday(p);
                int deltaMonth = pMonth - month;
                int deltaDay = pDay - day;
                if (deltaMonth == 0 && deltaDay == dayDelay) {
                    personListBirthday.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personListBirthday;
    }

    public List<Person> personForExcelMoney(final Person person) {
       //List<Person> listBirthday = personForExcelBirthday();
       List<Person> listAll = personService.findAll();
       List<Person> rezult = new ArrayList<Person>();
       for (Person all : listAll) {
           if (!all.equals(person)) {
                   rezult.add(all);
           }
       }
       return rezult;
    }
}
