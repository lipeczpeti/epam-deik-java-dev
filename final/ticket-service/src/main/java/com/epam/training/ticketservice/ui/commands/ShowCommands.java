package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.dto.UserDto;
import com.epam.training.ticketservice.domain.entity.Role;
import com.epam.training.ticketservice.service.ShowService;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class ShowCommands {

    private UserService userService;
    private ShowService showService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening")
    public String createShow(String movieTitle, String roomName, String startDate) {
        return showService.createShow(movieTitle, roomName, startDate);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening")
    public String deleteShow(String movieTitle, String roomName, String startDate) {
        return showService.deleteShow(movieTitle, roomName, startDate);
    }

    @ShellMethod(key = "list screenings")
    public String listShows() {
        return showService.listShows();
    }

    public Availability isAvailable() {
        Optional<UserDto> loggedIn = userService.getLoggedInUser();

        if (loggedIn.isPresent() && loggedIn.get().getRole().equals(Role.ADMIN)) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in with an admin account.");
        }
    }
}
