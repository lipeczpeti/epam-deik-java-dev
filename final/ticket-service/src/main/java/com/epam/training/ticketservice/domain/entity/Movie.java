package com.epam.training.ticketservice.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table
@Getter
@ToString
@Setter
public class Movie {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String title;
    private String category;
    private int length;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<Show> shows;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Actor> actors;

    public Movie(int id, String title, String category, int length) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.length = length;
        this.actors = new HashSet<>();
        this.shows = new ArrayList<>();
    }
}
