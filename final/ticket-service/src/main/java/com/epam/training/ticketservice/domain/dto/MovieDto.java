package com.epam.training.ticketservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MovieDto {
    private int id;
    private String title;
    private String category;
    private int length;
}
