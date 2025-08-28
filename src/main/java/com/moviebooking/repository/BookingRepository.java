package com.moviebooking.repository;

import com.moviebooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    Optional<Booking> findByBookingId(String bookingId);
    
    List<Booking> findByCustomerEmail(String customerEmail);
    
    List<Booking> findByMovieId(Long movieId);
    
    @Query("SELECT b FROM Booking b WHERE b.movie.id = :movieId AND b.bookingDate = :date AND b.showTime = :showTime")
    List<Booking> findByMovieAndDateTime(@Param("movieId") Long movieId, 
                                        @Param("date") LocalDate date, 
                                        @Param("showTime") LocalTime showTime);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.movie.id = :movieId AND b.bookingDate = :date AND b.showTime = :showTime AND b.status = 'CONFIRMED'")
    Integer countConfirmedBookingsForShow(@Param("movieId") Long movieId, 
                                         @Param("date") LocalDate date, 
                                         @Param("showTime") LocalTime showTime);
    
    List<Booking> findByStatus(String status);
    
    List<Booking> findByBookingDate(LocalDate bookingDate);
}