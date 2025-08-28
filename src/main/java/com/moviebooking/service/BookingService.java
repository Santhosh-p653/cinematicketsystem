package com.moviebooking.service;

import com.moviebooking.model.Booking;
import com.moviebooking.model.BookingStatus;
import com.moviebooking.model.Movie;
import com.moviebooking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final MovieService movieService;
    
    public BookingService(BookingRepository bookingRepository, MovieService movieService) {
        this.bookingRepository = bookingRepository;
        this.movieService = movieService;
    }
    
    @Transactional
    public Booking createBooking(Booking booking) {
        // Check if movie exists
        Movie movie = movieService.getMovieById(booking.getMovie().getId());
        
        // Check seat availability
        if (!isSeatsAvailable(movie.getId(), booking.getBookingDate(), booking.getShowTime(), booking.getNumberOfSeats())) {
            throw new IllegalStateException("Not enough seats available for this show");
        }
        
        // Set the movie reference properly
        booking.setMovie(movie);
        
        // Update available seats
        movieService.updateMovieSeats(movie.getId(), booking.getNumberOfSeats());
        
        return bookingRepository.save(booking);
    }
    
    public boolean isSeatsAvailable(Long movieId, LocalDate date, LocalTime showTime, int numberOfSeats) {
        Movie movie = movieService.getMovieById(movieId);
        
        // Check if the show time is valid for this movie
        if (!movie.getShowtimes().contains(showTime)) {
            throw new IllegalArgumentException("Invalid show time for this movie");
        }
        
        // Get total booked seats for this show
        Integer bookedSeats = bookingRepository.countConfirmedBookingsForShow(movieId, date, showTime);
        if (bookedSeats == null) {
            bookedSeats = 0;
        }
        
        int availableSeats = movie.getTotalSeats() - bookedSeats;
        return availableSeats >= numberOfSeats;
    }
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    
    public Optional<Booking> getBookingByBookingId(String bookingId) {
        return bookingRepository.findByBookingId(bookingId);
    }
    
    public List<Booking> getBookingsByCustomerEmail(String email) {
        return bookingRepository.findByCustomerEmail(email);
    }
    
    public List<Booking> getBookingsByMovieId(Long movieId) {
        return bookingRepository.findByMovieId(movieId);
    }
    
    public List<Booking> getBookingsByDate(LocalDate date) {
        return bookingRepository.findByBookingDate(date);
    }
    
    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed bookings can be cancelled");
        }
        
        // Release the seats
        movieService.releaseMovieSeats(booking.getMovie().getId(), booking.getNumberOfSeats());
        
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
    
    @Transactional
    public Booking cancelBookingByBookingId(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        return cancelBooking(booking.getId());
    }
    
    public Integer getAvailableSeatsForShow(Long movieId, LocalDate date, LocalTime showTime) {
        Movie movie = movieService.getMovieById(movieId);
        Integer bookedSeats = bookingRepository.countConfirmedBookingsForShow(movieId, date, showTime);
        if (bookedSeats == null) {
            bookedSeats = 0;
        }
        return movie.getTotalSeats() - bookedSeats;
    }
    
    @Transactional
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}