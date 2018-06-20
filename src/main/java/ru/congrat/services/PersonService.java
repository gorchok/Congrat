package ru.congrat.services;

import ru.congrat.model.Person;

import java.util.Date;
import java.util.List;

public interface PersonService {

    List<Person> findAll();

    List<Person> findByDate(Date date);

}
