package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.entity.Actor;
import com.epam.training.ticketservice.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Optional<Actor> findActorByName(String name) {
        return actorRepository.findByNameIgnoreCase(name);
    }
}
