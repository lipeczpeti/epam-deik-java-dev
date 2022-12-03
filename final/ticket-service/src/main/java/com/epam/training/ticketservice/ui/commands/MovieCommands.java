package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.dto.UserDto;
import com.epam.training.ticketservice.domain.entity.Actor;
import com.epam.training.ticketservice.domain.entity.Movie;
import com.epam.training.ticketservice.domain.entity.Role;
import com.epam.training.ticketservice.service.ActorService;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    private MovieService movieService;
    private ActorService actorService;
    private UserService userService;

    @ShellMethod(key = "admin movie add actor", value = "Adds new actor to a movie.")
    public String processActorAdd(String movieName, String actorName) {
        Optional<Actor> actor = actorService.findActorByName(actorName);
        Optional<Movie> movie = movieService.findByName(movieName);

        if (actor.isEmpty()) {
            return "Ilyen nevu szineszt nem talalt a rendszer!";

        } else if (movie.isEmpty()) {
            return "Ilyen nevu filmet nem talalt a rendszer!";
        }

        movie.get().getActors().add(actor.get());

        return actor.get().getName() + " sikeresen hozzáadva a " + movie.get().getTitle() + " filmhez";
    }

    @ShellMethod(key = "list movies", value = "Lists all movies in the database.")
    public String processList() {
        return movieService.findAll();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Adds new movie into the database.")
    public void processUpdate(String title, String category, int length) {
        movieService.update(title, category, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Adds new movie into the database.")
    public void processCreate(String title, String category, int length) {
        Movie movie = new Movie(0, title, category, length);

        try {
            movie = movieService.saveMovie(movie);
        } catch (ConstraintViolationException e) {
            // return "Hiba tortent mentes kozben";
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie")
    public void deleteMovie(String title) {
        int deletedMovies = movieService.deleteByTitle(title);

        // return deletedMovies + " movie(s) had been deleted from the database!";
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
