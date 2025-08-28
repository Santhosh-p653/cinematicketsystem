package com.moviebooking.service;

import com.moviebooking.model.Movie;
import com.moviebooking.repository.MovieRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.Arrays;
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
            // Sample movie data with enhanced information
            Movie movie1 = new Movie(
                "Avengers: Endgame",
                "Action",
                "181 min",
                "After the devastating events of Infinity War, the Avengers must assemble once more to reverse Thanos' actions and restore balance to the universe.",
                "https://via.placeholder.com/300x450/ff6b6b/ffffff?text=Avengers",
                "PG-13",
                "English",
                Arrays.asList(LocalTime.of(10, 0), LocalTime.of(13, 30), LocalTime.of(19, 0)),
                250.0,
                120
            );

            Movie movie2 = new Movie(
                "Joker",
                "Drama",
                "122 min",
                "A gritty character study of Arthur Fleck, a man disregarded by society, and his descent into madness and crime.",
                "https://via.placeholder.com/300x450/4ecdc4/ffffff?text=Joker",
                "R",
                "English",
                Arrays.asList(LocalTime.of(11, 0), LocalTime.of(16, 0), LocalTime.of(21, 30)),
                200.0,
                100
            );

            Movie movie3 = new Movie(
                "The Lion King",
                "Animation",
                "118 min",
                "A young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.",
                "https://via.placeholder.com/300x450/45b7d1/ffffff?text=Lion+King",
                "G",
                "English",
                Arrays.asList(LocalTime.of(9, 30), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0)),
                180.0,
                150
            );

            Movie movie4 = new Movie(
                "Interstellar",
                "Sci-Fi",
                "169 min",
                "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                "https://via.placeholder.com/300x450/96ceb4/ffffff?text=Interstellar",
                "PG-13",
                "English",
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(14, 0), LocalTime.of(20, 0)),
                220.0,
                80
            );

            movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3, movie4));
        }
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void updateMovieSeats(Long movieId, int seatsBooked) {
        Movie movie = getMovieById(movieId);
        movie.bookSeats(seatsBooked);
        movieRepository.save(movie);
    }

    public void releaseMovieSeats(Long movieId, int seatsReleased) {
        Movie movie = getMovieById(movieId);
        movie.releaseSeats(seatsReleased);
        movieRepository.save(movie);
    }
}