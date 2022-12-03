package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Optional<Actor> findByNameIgnoreCase(String name);
}
