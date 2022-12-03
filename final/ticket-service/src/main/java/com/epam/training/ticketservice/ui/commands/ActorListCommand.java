package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.entity.Actor;
import com.epam.training.ticketservice.service.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class ActorListCommand {

    private ActorService actorService;

    @ShellMethod(key = "admin actor list", value = "Lists all actors. Admin role required.")
    public String process() {
        List<Actor> actors = actorService.findAll();
        List<String> actorsAsString = new ArrayList<>();

        for (Actor actor : actors) {
            actorsAsString.add(actor.toString());
        }

        return "Az adatbazisban levo szineszek listaja:\n" + String.join("\n", actorsAsString);
    }
}
