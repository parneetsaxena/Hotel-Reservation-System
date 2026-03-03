package com.hotel.utils;

import com.hotel.models.Room;
import com.hotel.models.Booking;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String ROOMS_FILE = "data/rooms.txt";
    private static final String BOOKINGS_FILE = "data/bookings.txt";
    
    // Save rooms to file
    public static void saveRooms(List<Room> rooms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOMS_FILE))) {
            for (Room room : rooms) {
                writer.write(room.getRoomId() + "," + 
                           room.getRoomType() + "," + 
                           room.getPricePerNight() + "," + 
                           room.isAvailable() + "," + 
                           room.getCapacity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Load rooms from file
    public static List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        File file = new File(ROOMS_FILE);
        
        if (!file.exists()) {
            return initializeDefaultRooms();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Room room = new Room(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Double.parseDouble(parts[2]),
                        Integer.parseInt(parts[4])
                    );
                    room.setAvailable(Boolean.parseBoolean(parts[3]));
                    rooms.add(room);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return initializeDefaultRooms();
        }
        
        return rooms.isEmpty() ? initializeDefaultRooms() : rooms;
    }
    
    // Initialize default rooms
    private static List<Room> initializeDefaultRooms() {
        List<Room> rooms = new ArrayList<>();
        
        // Standard rooms
        rooms.add(new Room(101, "Standard", 100.0, 2));
        rooms.add(new Room(102, "Standard", 100.0, 2));
        rooms.add(new Room(103, "Standard", 100.0, 2));
        rooms.add(new Room(104, "Standard", 100.0, 2));
        rooms.add(new Room(105, "Standard", 100.0, 2));
        
        // Deluxe rooms
        rooms.add(new Room(201, "Deluxe", 200.0, 3));
        rooms.add(new Room(202, "Deluxe", 200.0, 3));
        rooms.add(new Room(203, "Deluxe", 200.0, 3));
        rooms.add(new Room(204, "Deluxe", 200.0, 3));
        
        // Suite rooms
        rooms.add(new Room(301, "Suite", 350.0, 4));
        rooms.add(new Room(302, "Suite", 350.0, 4));
        rooms.add(new Room(303, "Suite", 350.0, 4));
        
        saveRooms(rooms);
        return rooms;
    }
    
    // Save bookings to file
    public static void saveBookings(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking booking : bookings) {
                writer.write(booking.getBookingId() + "|" +
                           booking.getGuestName() + "|" +
                           booking.getPhoneNumber() + "|" +
                           booking.getEmail() + "|" +
                           booking.getRoomId() + "|" +
                           booking.getRoomType() + "|" +
                           booking.getCheckInDate().toString() + "|" +
                           booking.getCheckOutDate().toString() + "|" +
                           booking.getTotalAmount() + "|" +
                           booking.getPaymentMethod() + "|" +
                           booking.getStatus() + "|" +
                           booking.getBookingDate().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Load bookings from file
    public static List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(BOOKINGS_FILE);
        
        if (!file.exists()) {
            return bookings;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 12) {
                    Booking booking = new Booking(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        Integer.parseInt(parts[4]),
                        parts[5],
                        LocalDate.parse(parts[6]),
                        LocalDate.parse(parts[7]),
                        Double.parseDouble(parts[8]),
                        parts[9]
                    );
                    booking.setStatus(parts[10]);
                    booking.setBookingDate(LocalDate.parse(parts[11]));
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return bookings;
    }
}