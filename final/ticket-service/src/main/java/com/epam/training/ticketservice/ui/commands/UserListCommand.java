package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.entity.User;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class UserListCommand {

    private UserService userService;

    @ShellMethod(key = "list users", value = "List all users")
    public String process() {
        List<User> users = userService.findAll();

        String result = "";
        for (User user : users) {
            result += user + "\n";
        }

        return result;
    }
}
