package com.hotel.managers;

import com.hotel.models.Room;
import com.hotel.utils.FileManager;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RoomManager {
    private List<Room> rooms;
    
    public RoomManager() {
        this.rooms = FileManager.loadRooms();
    }
    
    public List<Room> getAllRooms() {
        return rooms;
    }
    
    public List<Room> getAvailableRooms() {
        return rooms.stream()
                   .filter(Room::isAvailable)
                   .collect(Collectors.toList());
    }
    
    public List<Room> searchRoomsByType(String roomType) {
        return rooms.stream()
                   .filter(room -> room.getRoomType().equalsIgnoreCase(roomType))
                   .filter(Room::isAvailable)
                   .collect(Collectors.toList());
    }
    
    public Room getRoomById(int roomId) {
        return rooms.stream()
                   .filter(room -> room.getRoomId() == roomId)
                   .findFirst()
                   .orElse(null);
    }
    
    public boolean updateRoomAvailability(int roomId, boolean isAvailable) {
        Room room = getRoomById(roomId);
        if (room != null) {
            room.setAvailable(isAvailable);
            saveRooms();
            return true;
        }
        return false;
    }
    
    public void saveRooms() {
        FileManager.saveRooms(rooms);
    }
    
    public List<String> getRoomTypes() {
        return rooms.stream()
                   .map(Room::getRoomType)
                   .distinct()
                   .collect(Collectors.toList());
    }
}