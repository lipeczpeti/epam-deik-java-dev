package com.epam.training.ticketservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table
@Getter
@Setter
@ToString
public class Cinema {
    @Id
    @GeneratedValue
    private int id;
    private double positionX;
    private double positionY;
    private String name;

    @OneToMany(mappedBy = "cinema")
    private List<Room> rooms;

    public Cinema(double positionX, double positionY, String name) {
        this.id = 0;
        this.positionX = positionX;
        this.positionY = positionY;
        this.name = name;
        this.rooms = new ArrayList<>();
    }
}
