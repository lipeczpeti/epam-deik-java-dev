package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.dto.UserDto;
import com.epam.training.ticketservice.domain.entity.Role;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class AdminCommand {

    @Autowired
    private UserService userService;

    @ShellMethod(key = "sign in privileged")
    public String login(String name, String password) {
        if (userService.loginAdmin(name, password)) {
            return "";

        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(key = "sign out")
    public void logout() {
        userService.logout();
    }

    @ShellMethod(key = "describe account")
    public String describe() {
        Optional<UserDto> loggedInUser = userService.getLoggedInUser();

        if (loggedInUser.isPresent() && loggedInUser.get().getRole().equals(Role.ADMIN)) {
            return "Signed in with privileged account '" + loggedInUser.get().getName() + "'";
        } else {
            return "You are not signed in";
        }
    }
}
