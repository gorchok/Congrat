package ru.congrat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.congrat.model.Person;
import ru.congrat.services.PersonService;

import java.util.List;

@PropertySource("classpath:templates/application.properties")
@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }*/

    public static void main(String[] args) throws Exception {
            SpringApplication.run(Application.class, args);
        }
    }
