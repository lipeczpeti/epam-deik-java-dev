package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.domain.dto.UserDto;
import com.epam.training.ticketservice.domain.entity.Role;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommands {

    private UserService userService;
    private RoomService roomService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room")
    public String createRoom(String name, int chairRows, int chairColumns) {
        return roomService.createRoom(name, chairColumns, chairRows);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room")
    public String updateRoom(String name, int chairColumns, int chairRows) {
        return roomService.updateRoom(name, chairColumns, chairRows);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room")
    public String deleteRoom(String name) {
        return roomService.deleteRoom(name);
    }

    @ShellMethod(key = "list rooms")
    public String listRooms() {
        return roomService.listRooms();
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
