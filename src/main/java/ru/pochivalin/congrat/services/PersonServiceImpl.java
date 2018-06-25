package ru.pochivalin.congrat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pochivalin.congrat.model.Person;
import ru.pochivalin.congrat.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
    return personRepository.findAll();
    }

    @Override
    public List<Person> findByDate(Date date) {
        return personRepository.findByDate(date);
    }

    public String getBorn(String sex) {
       String born = null;
       if(sex.equals("м")) {
          born = "родился";
       }
       else {
          born = "родилась";
       }
       return born;
    }

    public int getMonthBirthday(Person person){
        Calendar cal = Calendar.getInstance();
        cal.setTime(person.getDate());
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    public String monthIntToStr(int num) {
      String[] month = new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
      return month[num];
    }

    public int getDayBirthday(Person person){
        Calendar cal = Calendar.getInstance();
        cal.setTime(person.getDate());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public List<String> getAllEmail() {
      List<Person> list = personRepository.findAll();
      List<String> email = new ArrayList<String>();
      for(Person p : list) {
          email.add(p.getEmail());
      }
      return email;
    }

}
