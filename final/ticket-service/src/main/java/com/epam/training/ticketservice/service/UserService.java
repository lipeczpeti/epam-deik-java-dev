package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.dto.UserDto;
import com.epam.training.ticketservice.domain.entity.Role;
import com.epam.training.ticketservice.domain.entity.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Optional<UserDto> loggedInUser = Optional.empty();

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean loginAdmin(String name, String password) {
        Optional<UserDto> fetchedUser = findByNameAndPassword(name, password); /**/

        if (fetchedUser.isEmpty()) {
            return false;

        } else if (fetchedUser.isPresent() && !fetchedUser.get().getRole().equals(Role.ADMIN)) {
            return false;

        } else {
            this.loggedInUser = fetchedUser;

            return true;
        }
    }

    /*public boolean login(String name, String password) {
        Optional<UserDto> fetchedUser = findByNameAndPassword(name, password);

        if (fetchedUser.isEmpty()) {
            return false;

        } else {
            this.loggedInUser = fetchedUser;

            return true;
        }
    }*/

    public Optional<UserDto> findByNameAndPassword(String name, String password) {
        Optional<User> fetchedUser = userRepository.findByNameAndPassword(name, password); /**/

        if (fetchedUser.isEmpty()) {
            return Optional.empty();

        } else {
            UserDto userDto = new UserDto();
            userDto.setId(fetchedUser.get().getId());
            userDto.setEmail(fetchedUser.get().getEmail());
            userDto.setName(fetchedUser.get().getName());
            userDto.setRole(fetchedUser.get().getRole());

            return Optional.of(userDto);
        }
    }

    public void logout() {
        this.loggedInUser = Optional.empty();
    }

    public Optional<UserDto> getLoggedInUser() {
        return this.loggedInUser;
    }
}
