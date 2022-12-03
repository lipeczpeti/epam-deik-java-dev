package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByNameIgnoreCase(String name);

    @Transactional
    Integer deleteByName(String name);
}
