package com.hotel;

import com.hotel.managers.BookingManager;
import com.hotel.managers.RoomManager;
import com.hotel.views.HomePage;
import javafx.application.Application;
import javafx.stage.Stage;

public class HotelReservationApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize managers
            RoomManager roomManager = new RoomManager();
            BookingManager bookingManager = new BookingManager(roomManager);
            
            // Create home page
            HomePage homePage = new HomePage(primaryStage, roomManager, bookingManager);
            
            // Set up stage
            primaryStage.setTitle("Hotel Reservation System");
            primaryStage.setScene(homePage.createScene());
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
