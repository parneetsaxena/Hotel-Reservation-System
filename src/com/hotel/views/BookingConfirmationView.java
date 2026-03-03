package com.hotel.views;

import com.hotel.managers.BookingManager;
import com.hotel.managers.RoomManager;
import com.hotel.models.Booking;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BookingConfirmationView {
    private Stage stage;
    private RoomManager roomManager;
    private BookingManager bookingManager;
    private String bookingId;
    
    public BookingConfirmationView(Stage stage, RoomManager roomManager, 
                                  BookingManager bookingManager, String bookingId) {
        this.stage = stage;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
        this.bookingId = bookingId;
    }
    
    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        VBox centerSection = new VBox(20);
        centerSection.setPadding(new Insets(40));
        centerSection.setAlignment(Pos.CENTER);
        
        // Success message
        Label successIcon = new Label("✓");
        successIcon.setStyle("-fx-font-size: 80px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
        
        Label successLabel = new Label("Booking Confirmed!");
        successLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
        
        Label messageLabel = new Label("Your booking has been successfully confirmed.");
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;");
        
        // Booking details
        Booking booking = bookingManager.getBookingById(bookingId);
        
        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(20));
        detailsBox.setMaxWidth(500);
        detailsBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 10;");
        
        Label bookingIdLabel = new Label("Booking ID: " + bookingId);
        bookingIdLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label guestLabel = new Label("Guest: " + booking.getGuestName());
        guestLabel.setStyle("-fx-font-size: 14px;");
        
        Label roomLabel = new Label("Room: #" + booking.getRoomId() + " - " + booking.getRoomType());
        roomLabel.setStyle("-fx-font-size: 14px;");
        
        Label datesLabel = new Label("Dates: " + booking.getCheckInDate() + " to " + booking.getCheckOutDate());
        datesLabel.setStyle("-fx-font-size: 14px;");
        
        Label nightsLabel = new Label("Nights: " + booking.getNumberOfNights());
        nightsLabel.setStyle("-fx-font-size: 14px;");
        
        Label amountLabel = new Label("Total Amount: $" + String.format("%.2f", booking.getTotalAmount()));
        amountLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        Label paymentLabel = new Label("Payment Method: " + booking.getPaymentMethod());
        paymentLabel.setStyle("-fx-font-size: 14px;");
        
        Label statusLabel = new Label("Status: " + booking.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
        
        detailsBox.getChildren().addAll(bookingIdLabel, guestLabel, roomLabel, datesLabel, 
                                       nightsLabel, amountLabel, paymentLabel, statusLabel);
        
        // Action buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button viewBookingsButton = new Button("View My Bookings");
        viewBookingsButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 12 25 12 25; " +
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        viewBookingsButton.setOnAction(e -> {
            MyBookingsView bookingsView = new MyBookingsView(stage, roomManager, bookingManager);
            stage.setScene(bookingsView.createScene());
        });
        
        Button homeButton = new Button("Back to Home");
        homeButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 12 25 12 25; " +
            "-fx-background-color: #27ae60; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        homeButton.setOnAction(e -> {
            HomePage homePage = new HomePage(stage, roomManager, bookingManager);
            stage.setScene(homePage.createScene());
        });
        
        buttonBox.getChildren().addAll(viewBookingsButton, homeButton);
        
        centerSection.getChildren().addAll(successIcon, successLabel, messageLabel, detailsBox, buttonBox);
        
        root.setCenter(centerSection);
        
        return new Scene(root, 800, 600);
    }
}