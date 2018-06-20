package ru.congrat.sheduled;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.congrat.model.Person;
import ru.congrat.services.PersonService;

@Component
public class PersonForBirthday {

    @Autowired
    PersonService personService;

    public List<Person> personFilter() throws Exception {
        List<Person> personListBirthday = new ArrayList<Person>();
        try {
            List<Person> listPerson = personService.findAll();
            Calendar currentDate = Calendar.getInstance();
            //System.out.println("call url " + currentDate.getTime());
            int month = currentDate.get(Calendar.MONTH) + 1;
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            //System.out.println(day + " " + month);

            for (Person p : listPerson) {
                Calendar pDate = Calendar.getInstance();
                pDate.setTime(p.getDate());
                int pMonth = pDate.get(Calendar.MONTH) + 1;
                int pDay = pDate.get(Calendar.DAY_OF_MONTH);
                if (month == pMonth && day == pDay) {
                    personListBirthday.add(p);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return personListBirthday;
    }

}