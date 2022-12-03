package com.epam.training.ticketservice.configuration;

import com.epam.training.ticketservice.service.DatabaseSeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;

@Component
public class DbSeederConfiguration {

    @Autowired
    private DatabaseSeederService databaseSeederService;

    @PostConstruct
    public void seedDatabase() {
        try {
            databaseSeederService.seedDatabase();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
