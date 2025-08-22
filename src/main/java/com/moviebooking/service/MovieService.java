package com.moviebooking.service;

import com.moviebooking.model.Movie;
import com.moviebooking.repository.MovieRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void initSampleData() {
        if (movieRepository.count() == 0) {
            movieRepository.save(new Movie("Avengers: Endgame", "Action", "181 min"));
            movieRepository.save(new Movie("Joker", "Drama", "122 min"));
            movieRepository.save(new Movie("The Lion King", "Animation", "118 min"));
        }
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}