package com.epam.training.ticketservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@NoArgsConstructor
@Entity
@Table
@Getter
@ToString
public class Chair {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    private int roomRow;
    private int chairNumber;
}
