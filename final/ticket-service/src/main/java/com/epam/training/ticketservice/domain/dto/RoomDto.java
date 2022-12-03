package com.epam.training.ticketservice.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomDto {
    private String name;
    private int rows;
    private int columns;
    private int chairs;

    @Override
    public String toString() {
        return "Room " + name + " with " + chairs + " seats, " + rows + " rows and " + columns + " columns";
    }
}
