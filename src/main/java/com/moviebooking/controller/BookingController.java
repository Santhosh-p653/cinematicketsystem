package com.moviebooking.controller;

import com.moviebooking.model.Booking;
import com.moviebooking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final BookingService bookingService;
    
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(createdBooking);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/booking-id/{bookingId}")
    public ResponseEntity<Booking> getBookingByBookingId(@PathVariable String bookingId) {
        Optional<Booking> booking = bookingService.getBookingByBookingId(bookingId);
        return booking.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Booking>> getBookingsByCustomerEmail(@PathVariable String email) {
        return ResponseEntity.ok(bookingService.getBookingsByCustomerEmail(email));
    }
    
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Booking>> getBookingsByMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(bookingService.getBookingsByMovieId(movieId));
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Booking>> getBookingsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(bookingService.getBookingsByDate(date));
    }
    
    @GetMapping("/availability")
    public ResponseEntity<Integer> getAvailableSeats(
            @RequestParam Long movieId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime showTime) {
        try {
            Integer availableSeats = bookingService.getAvailableSeatsForShow(movieId, date, showTime);
            return ResponseEntity.ok(availableSeats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(-1);
        }
    }
    
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        try {
            Booking cancelledBooking = bookingService.cancelBooking(id);
            return ResponseEntity.ok(cancelledBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/booking-id/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBookingByBookingId(@PathVariable String bookingId) {
        try {
            Booking cancelledBooking = bookingService.cancelBookingByBookingId(bookingId);
            return ResponseEntity.ok(cancelledBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}