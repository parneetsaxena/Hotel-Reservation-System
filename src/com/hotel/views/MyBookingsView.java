package com.hotel.views;

import com.hotel.managers.BookingManager;
import com.hotel.managers.RoomManager;
import com.hotel.models.Booking;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class MyBookingsView {
    private Stage stage;
    private RoomManager roomManager;
    private BookingManager bookingManager;
    private ListView<Booking> bookingListView;
    
    public MyBookingsView(Stage stage, RoomManager roomManager, BookingManager bookingManager) {
        this.stage = stage;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
    }
    
    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Top section
        VBox topSection = new VBox(10);
        topSection.setPadding(new Insets(20));
        topSection.setStyle("-fx-background-color: #34495e;");
        
        Label titleLabel = new Label("My Bookings");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        topSection.getChildren().add(titleLabel);
        
        // Center section
        VBox centerSection = new VBox(15);
        centerSection.setPadding(new Insets(20));
        
        bookingListView = new ListView<>();
        bookingListView.setStyle("-fx-font-size: 14px;");
        bookingListView.setCellFactory(param -> new ListCell<Booking>() {
            @Override
            protected void updateItem(Booking booking, boolean empty) {
                super.updateItem(booking, empty);
                if (empty || booking == null) {
                    setText(null);
                    setStyle("");
                } else {
                    String statusColor = booking.getStatus().equals("Confirmed") ? "#27ae60" : "#c0392b";
                    setText(String.format(
                        "Booking ID: %s | Guest: %s | Room #%d (%s) | Dates: %s to %s | Amount: $%.2f | Status: %s",
                        booking.getBookingId(),
                        booking.getGuestName(),
                        booking.getRoomId(),
                        booking.getRoomType(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getTotalAmount(),
                        booking.getStatus()
                    ));
                    setStyle("-fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-width: 0 0 1 0;");
                }
            }
        });
        
        List<Booking> allBookings = bookingManager.getAllBookings();
        bookingListView.setItems(FXCollections.observableArrayList(allBookings));
        
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        viewDetailsButton.setOnAction(e -> {
            Booking selected = bookingListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showBookingDetails(selected);
            } else {
                showAlert("No Selection", "Please select a booking to view details.", Alert.AlertType.WARNING);
            }
        });
        
        Button cancelButton = new Button("Cancel Booking");
        cancelButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-color: #e74c3c; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        cancelButton.setOnAction(e -> {
            Booking selected = bookingListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (selected.getStatus().equals("Cancelled")) {
                    showAlert("Already Cancelled", "This booking is already cancelled.", Alert.AlertType.INFORMATION);
                } else {
                    cancelBooking(selected);
                }
            } else {
                showAlert("No Selection", "Please select a booking to cancel.", Alert.AlertType.WARNING);
            }
        });
        
        buttonBox.getChildren().addAll(viewDetailsButton, cancelButton);
        
        centerSection.getChildren().addAll(bookingListView, buttonBox);
        VBox.setVgrow(bookingListView, Priority.ALWAYS);
        
        // Bottom section
        HBox bottomSection = new HBox(10);
        bottomSection.setPadding(new Insets(15));
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setStyle("-fx-background-color: #34495e;");
        
        Button backButton = new Button("Back to Home");
        backButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-color: #7f8c8d; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(stage, roomManager, bookingManager);
            stage.setScene(homePage.createScene());
        });
        
        bottomSection.getChildren().add(backButton);
        
        root.setTop(topSection);
        root.setCenter(centerSection);
        root.setBottom(bottomSection);
        
        return new Scene(root, 800, 600);
    }
    
    private void showBookingDetails(Booking booking) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Booking Details");
        alert.setHeaderText("Booking ID: " + booking.getBookingId());
        
        String details = String.format(
            "Guest Name: %s\n" +
            "Phone: %s\n" +
            "Email: %s\n" +
            "Room: #%d - %s\n" +
            "Check-in: %s\n" +
            "Check-out: %s\n" +
            "Number of Nights: %d\n" +
            "Total Amount: $%.2f\n" +
            "Payment Method: %s\n" +
            "Booking Date: %s\n" +
            "Status: %s",
            booking.getGuestName(),
            booking.getPhoneNumber(),
            booking.getEmail(),
            booking.getRoomId(),
            booking.getRoomType(),
            booking.getCheckInDate(),
            booking.getCheckOutDate(),
            booking.getNumberOfNights(),
            booking.getTotalAmount(),
            booking.getPaymentMethod(),
            booking.getBookingDate(),
            booking.getStatus()
        );
        
        alert.setContentText(details);
        alert.showAndWait();
    }
    
    private void cancelBooking(Booking booking) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Cancel Booking");
        confirmAlert.setHeaderText("Are you sure you want to cancel this booking?");
        confirmAlert.setContentText("Booking ID: " + booking.getBookingId());
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = bookingManager.cancelBooking(booking.getBookingId());
            if (success) {
                showAlert("Success", "Booking cancelled successfully.", Alert.AlertType.INFORMATION);
                bookingListView.setItems(FXCollections.observableArrayList(bookingManager.getAllBookings()));
            } else {
                showAlert("Error", "Failed to cancel booking.", Alert.AlertType.ERROR);
            }
        }
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}