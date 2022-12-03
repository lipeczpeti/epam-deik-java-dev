package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.entity.User;
import com.epam.training.ticketservice.domain.entity.Role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@AllArgsConstructor
public class DatabaseSeederService {

    private UserService userService;

    public void seedDatabase() throws ParseException {
        User user = new User();
        user.setEmail("admin@admin.hu");
        user.setName("admin");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);

        userService.saveUser(user);
    }
}
