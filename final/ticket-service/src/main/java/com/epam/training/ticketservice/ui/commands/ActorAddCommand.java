package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.entity.Actor;
import com.epam.training.ticketservice.service.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ShellComponent
@AllArgsConstructor
public class ActorAddCommand {

    private ActorService actorService;

    @ShellMethod(key = "admin actor add", value = "Adds new actor into the database. Admin role required.")
    public String process(String name, String birthday) {
        Date finalBirthday = null;
        try {
            finalBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        } catch (ParseException e) {
            return "Nem megfelelo datum formatum! (pl.: 2011-01-01)";
        }

        Actor actor = new Actor(0, name, finalBirthday);

        actor = actorService.saveActor(actor);

        return "Az uj szinesz letrejott " + actor.getId() + " id-val!";
    }
}
