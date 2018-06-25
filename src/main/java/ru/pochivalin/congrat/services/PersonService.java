package ru.pochivalin.congrat.services;

import ru.pochivalin.congrat.model.Person;
import java.util.Date;
import java.util.List;

public interface PersonService {

    List<Person> findAll();

    List<Person> findByDate(Date date);

    int getMonthBirthday(Person person);

    int getDayBirthday(Person person);

    List<String> getAllEmail();

    String monthIntToStr(int num);

    String getBorn(String sex);

}
