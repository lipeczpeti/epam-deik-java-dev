package com.epam.training.ticketservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import java.util.List;

@NoArgsConstructor
@Entity
@Table
@Getter
@Setter
@ToString
public class Room {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int rows;
    private int columns;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "room")
    private List<Chair> chairs;
}
