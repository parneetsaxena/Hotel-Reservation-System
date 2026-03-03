package com.hotel.managers;

import com.hotel.models.Booking;
import com.hotel.models.Room;
import com.hotel.utils.FileManager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingManager {
    private List<Booking> bookings;
    private RoomManager roomManager;
    
    public BookingManager(RoomManager roomManager) {
        this.bookings = FileManager.loadBookings();
        this.roomManager = roomManager;
    }
    
    public String createBooking(String guestName, String phoneNumber, String email,
                                int roomId, LocalDate checkInDate, LocalDate checkOutDate,
                                String paymentMethod) {
        
        Room room = roomManager.getRoomById(roomId);
        if (room == null || !room.isAvailable()) {
            return null;
        }
        
        // Calculate total amount
        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalAmount = numberOfNights * room.getPricePerNight();
        
        // Generate booking ID
        String bookingId = "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Create booking
        Booking booking = new Booking(
            bookingId, guestName, phoneNumber, email,
            roomId, room.getRoomType(), checkInDate, checkOutDate,
            totalAmount, paymentMethod
        );
        
        bookings.add(booking);
        roomManager.updateRoomAvailability(roomId, false);
        saveBookings();
        
        return bookingId;
    }
    
    public boolean cancelBooking(String bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking != null && booking.getStatus().equals("Confirmed")) {
            booking.setStatus("Cancelled");
            roomManager.updateRoomAvailability(booking.getRoomId(), true);
            saveBookings();
            return true;
        }
        return false;
    }
    
    public Booking getBookingById(String bookingId) {
        return bookings.stream()
                      .filter(booking -> booking.getBookingId().equals(bookingId))
                      .findFirst()
                      .orElse(null);
    }
    
    public List<Booking> getAllBookings() {
        return bookings;
    }
    
    public List<Booking> getActiveBookings() {
        return bookings.stream()
                      .filter(booking -> booking.getStatus().equals("Confirmed"))
                      .collect(Collectors.toList());
    }
    
    public void saveBookings() {
        FileManager.saveBookings(bookings);
    }
}