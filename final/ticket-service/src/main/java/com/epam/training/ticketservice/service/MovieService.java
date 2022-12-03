package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.dto.MovieDto;
import com.epam.training.ticketservice.domain.entity.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public String findAll() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> moviesDto = new ArrayList<>();

        movies.forEach(movie -> {
            MovieDto movieDto = new MovieDto();
            movieDto.setId(movie.getId());
            movieDto.setCategory(movie.getCategory());
            movieDto.setLength(movie.getLength());
            movieDto.setTitle(movie.getTitle());

            moviesDto.add(movieDto);
        });

        if (moviesDto.size() == 0) {
            return "There are no movies at the moment";
        } else {
            String results = "";

            for (MovieDto movieDto : moviesDto) {
                results += movieDto.getTitle() + " (" + movieDto.getCategory() + ", " + movieDto.getLength()
                        + " minutes)\n";
            }

            return results;
        }
    }

    public Optional<Movie> findByName(String name) {
        return movieRepository.findByTitleIgnoreCase(name);
    }

    public boolean update(String name, String category, int length) {
        Optional<Movie> movie = findByName(name);

        if (movie.isPresent()) {
            movie.get().setCategory(category);
            movie.get().setLength(length);

            movieRepository.save(movie.get());

            return true;
        }

        return false;
    }

    public int deleteByTitle(String title) {
        List<Movie> movies = movieRepository.deleteByTitleIgnoreCase(title);

        return movies.size();
    }
}
