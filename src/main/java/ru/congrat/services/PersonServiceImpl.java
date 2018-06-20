package ru.congrat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.congrat.model.Person;
import ru.congrat.repository.PersonRepository;

import java.util.Date;
import java.util.List;

@ComponentScan({"ru.congrat"})
@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
    return personRepository.findAll();
    }

    @Override
    public List<Person> findByDate(Date date) {
        return personRepository.findByDate(date);
    }
}
