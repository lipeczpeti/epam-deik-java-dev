package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.entity.Cinema;
import com.epam.training.ticketservice.service.CinemaService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class CinemaListCommand {

    private CinemaService cinemaService;

    @ShellMethod(key = "admin cinema list", value = "Adds new cinema into the database. Admin role required.")
    public String process() {

        List<Cinema> cinemas = cinemaService.findAll();
        List<String> cinemasAsString = new ArrayList<>();

        for (Cinema cinema : cinemas) {
            cinemasAsString.add(cinema.toString());
        }

        return "Az adatbazisban levo mozik listaja:\n" + String.join("\n", cinemasAsString);
    }
}
