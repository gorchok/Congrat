package ru.pochivalin.congrat.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pochivalin.congrat.model.Person;
import ru.pochivalin.congrat.repository.PersonRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceImplTest {

    @Autowired
    private PersonService personService;

    @Test
    public void findAllTest() {
        List<Person> list = personService.findAll();

        Assert.assertEquals(5,list.size());
    }

    @Test
    public void findByDateTest() {
        try {
            String testFirstName = "Алексей";
            String testLastName = "Почивалин";
            String testDate = "04.07.1984";
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse(testDate);

            List<Person> list = personService.findByDate(date);
            String firstName = list.get(0).getFirsName();
            String lastName = list.get(0).getLastName();

            Assert.assertEquals(testFirstName,firstName);
            Assert.assertEquals(testLastName,lastName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMonthBirthdayTest() {
        try {
            Person person = new Person();
            String testMonth = "07";
            String testDate = "04." + testMonth + ".1984";
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse(testDate);
            person.setDate(date);

            int monthBirthday = personService.getMonthBirthday(person);

            Assert.assertEquals(Integer.parseInt(testMonth),monthBirthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDayBirthdayTest() {
        try {
            Person person = new Person();
            String testDay = "04";
            String testDate = testDay + ".07.1984";
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse(testDate);
            person.setDate(date);

            int dayBirthday = personService.getDayBirthday(person);

            Assert.assertEquals(Integer.parseInt(testDay),dayBirthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllEmailTest() {
        List<String> listTestEmail = personService.getAllEmail();

        Assert.assertEquals(5, listTestEmail.size());
    }

    @Test
    public void monthIntToStrTest() {
        String[] testMonth = new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        for (int i = 1; i <= 12; i++) {
            String month = personService.monthIntToStr(i);
            Assert.assertEquals(testMonth[i-1], month);
        }
    }

    @Test
    public void getBornTest() {
        String testBornMale = "м";
        String testBornMaleResult = "родился";
        String testBornFemale = "ж";
        String testBornFemaleResult = "родилась";

        String bornMale = personService.getBorn(testBornMale);
        String bornFemale = personService.getBorn(testBornFemale);

        Assert.assertEquals(testBornMaleResult,bornMale);
        Assert.assertEquals(testBornFemaleResult,bornFemale);
    }
}
