package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findByTitleIgnoreCase(String title);

    @Transactional
    List<Movie> deleteByTitleIgnoreCase(String title);
}
