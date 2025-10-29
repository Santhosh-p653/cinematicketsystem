package com.moviebooking.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String title;
    
    @Column(nullable = false)
    private String genre;
    
    @Column(nullable = false)
    private String duration;
    
    @Column(length = 1000)
    private String description;
    
    private String posterUrl;
    
    private String rating;
    
    private String language;
    
    @ElementCollection
    @CollectionTable(name = "movie_showtimes", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "showtime")
    private List<LocalTime> showtimes;
    
    @Column(nullable = false)
    private Double ticketPrice = 150.0;
    
    @Column(nullable = false)
    private Integer totalSeats = 100;
    
    @Column(nullable = false)
    private Integer availableSeats = 100;

    public Movie() {}

    public Movie(String title, String genre, String duration, String description,
                String posterUrl, String rating, String language, List<LocalTime> showtimes,
                Double ticketPrice, Integer totalSeats) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.language = language;
        this.showtimes = showtimes;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<LocalTime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<LocalTime> showtimes) {
        this.showtimes = showtimes;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void bookSeats(int numberOfSeats) {
        if (this.availableSeats >= numberOfSeats) {
            this.availableSeats -= numberOfSeats;
        } else {
            throw new IllegalStateException("Not enough available seats");
        }
    }

    public void releaseSeats(int numberOfSeats) {
        this.availableSeats = Math.min(this.availableSeats + numberOfSeats, this.totalSeats);
    }
}