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

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "chair_id")
    private Chair chair;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
    private int price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private boolean used;
}
