package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.entity.Cinema;
import com.epam.training.ticketservice.service.CinemaService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class CinemaAddCommand  {

    private CinemaService cinemaService;

    @ShellMethod(key = "admin cinema add", value = "Adds new cinema into the database. Admin role required.")
    public String process(String name, Double longitudinalCoordinate, Double latitudeCoordinate) {
        Cinema cinema = new Cinema(longitudinalCoordinate, latitudeCoordinate, name);

        cinema = cinemaService.saveCinema(cinema);

        return "Az uj mozi letrejott " + cinema.getId() + " id-val!";
    }
}
