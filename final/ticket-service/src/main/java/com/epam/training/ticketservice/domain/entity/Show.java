package com.epam.training.ticketservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Show {

    @Id
    @GeneratedValue
    private int id;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    private int price;
}
