package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.service.DatabaseSeederService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;

@ShellComponent
@AllArgsConstructor
public class DatabaseSeedCommand {

    private DatabaseSeederService databaseSeederService;

    @ShellMethod(key = "seed database", value = "Adds movies and actors to the database. Admin role required.")
    public String process() {
        try {
            databaseSeederService.seedDatabase();

            return "Sikeres adatbazis inicializalas!";
        } catch (ParseException e) {
            return "Sikertelen adatbazis inicializalas!";
        }
    }
}
