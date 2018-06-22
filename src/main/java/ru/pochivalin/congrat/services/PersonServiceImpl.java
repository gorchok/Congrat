package ru.pochivalin.congrat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pochivalin.congrat.model.Person;
import ru.pochivalin.congrat.repository.PersonRepository;

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
}
