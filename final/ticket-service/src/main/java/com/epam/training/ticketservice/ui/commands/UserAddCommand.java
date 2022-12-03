package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.entity.Role;
import com.epam.training.ticketservice.domain.entity.User;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class UserAddCommand {

    private UserService userService;

    @ShellMethod(key = "customer add", value = "Adds new customer into the database.")
    public String process(String name, String password, String email) {
        User user = new User(0, name, email, password, Role.USER);

        user = userService.saveUser(user);

        return "Az uj felhasznalo letrejott " + user.getId() + " id-val!";
    }
}
