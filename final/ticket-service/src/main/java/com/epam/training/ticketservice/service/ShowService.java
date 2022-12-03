package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.dto.ShowDto;
import com.epam.training.ticketservice.domain.entity.Movie;
import com.epam.training.ticketservice.domain.entity.Room;
import com.epam.training.ticketservice.domain.entity.Show;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ShowService {

    private ShowRepository showRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    public String createShow(String movieTitle, String roomName, String startDate) {
        Date movieStartDate = null;
        try {
            movieStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDate);
        } catch (ParseException e) {
            return "Nem megfelelo datum formatum! (pl.: 2011-01-01 14:44)";
        }

        Optional<Room> room = roomRepository.findByNameIgnoreCase(roomName);
        Optional<Movie> movie = movieRepository.findByTitleIgnoreCase(movieTitle);

        if (room.isPresent() && movie.isPresent()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(movieStartDate);
            calendar.add(calendar.MINUTE, -10);
            Date movieStartDateWithBreak = calendar.getTime();

            calendar.add(Calendar.MINUTE, movie.get().getLength() + 10);
            Date movieEndDate = calendar.getTime();

            List<Show> shows = showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(
                    movieStartDate, movieStartDate, room.get());
            List<Show> showsWithBreak
                    = showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(
                            movieStartDate, movieStartDateWithBreak, room.get());

            if (shows.size() > 0) {
                return "There is an overlapping screening";

            } else if (showsWithBreak.size() > 0) {
                return "This would start in the break period after another screening in this room";

            } else {
                Show show = new Show();
                show.setMovie(movie.get());
                show.setRoom(room.get());
                show.setStartDate(movieStartDate);
                show.setEndDate(movieEndDate);

                showRepository.save(show);

                return "Screening creation is successful";
            }
        } else {
            return "Room name or movie title is not correct!";
        }
    }

    public String deleteShow(String movieTitle, String roomName, String startDate) {
        Date movieStartDate = null;
        try {
            movieStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDate);
        } catch (ParseException e) {
            return "Nem megfelelo datum formatum! (pl.: 2011-01-01 14:44)";
        }

        Optional<Room> room = roomRepository.findByNameIgnoreCase(roomName);
        Optional<Movie> movie = movieRepository.findByTitleIgnoreCase(movieTitle);

        if (room.isPresent() && movie.isPresent()) {
            int deletedRows = showRepository.deleteByStartDateEqualsAndMovieEqualsAndRoomEquals(
                    movieStartDate, movie.get(), room.get());

            if (deletedRows == 0) {
                return "There was no screening at this time!";
            } else {
                return "Screening delete successful!";
            }
        } else {
            return "There is no room or movie with this name.";
        }
    }

    public String listShows() {
        List<Show> shows = showRepository.findAll();
        List<ShowDto> showDtos = new ArrayList<>();

        for (Show show : shows) {
            showDtos.add(ShowDto.builder()
                            .startDate(show.getStartDate())
                            .movie(show.getMovie().getTitle())
                            .id(show.getId())
                            .room(show.getRoom().getName())
                            .movieCategory(show.getMovie().getCategory().toString())
                            .movieLength(show.getMovie().getLength())
                            .build());
        }

        if (showDtos.size() == 0) {
            return "There are no screenings";
        } else {
            String result = "";
            for (ShowDto showDto : showDtos) {
                result += showDto.toString() + "\n";
            }

            return result;
        }
    }

}
