package com.epam.training.ticketservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Getter
@Setter
@ToString
public class Actor {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Date birthday;
}
