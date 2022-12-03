package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.entity.Movie;
import com.epam.training.ticketservice.domain.entity.Room;
import com.epam.training.ticketservice.domain.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer>  {

    List<Show> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(
            Date startDate, Date endDate, Room room);

    @Transactional
    int deleteByStartDateEqualsAndMovieEqualsAndRoomEquals(Date startDate, Movie movie, Room room);
}
