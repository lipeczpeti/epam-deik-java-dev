package com.epam.training.ticketservice.domain.dto;

import com.epam.training.ticketservice.domain.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private int id;
    private String name;
    private String email;
    private Role role;
}
