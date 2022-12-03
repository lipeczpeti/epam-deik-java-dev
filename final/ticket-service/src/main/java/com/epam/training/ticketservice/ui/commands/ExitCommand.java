package com.epam.training.ticketservice.ui.commands;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ExitCommand {

    @ShellMethod(key = "exit")
    public void exit() {
        System.exit(0);
    }
}
