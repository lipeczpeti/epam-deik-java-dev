package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.entity.Cinema;
import com.epam.training.ticketservice.repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {

    private CinemaRepository cinemaRepository;

    public Cinema saveCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }
}
