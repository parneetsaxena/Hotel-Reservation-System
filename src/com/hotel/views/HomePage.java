package com.hotel.views;

import com.hotel.managers.BookingManager;
import com.hotel.managers.RoomManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {
    private Stage stage;
    private RoomManager roomManager;
    private BookingManager bookingManager;
    
    public HomePage(Stage stage, RoomManager roomManager, BookingManager bookingManager) {
        this.stage = stage;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
    }
    
    public Scene createScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #3498db);");
        
        Label titleLabel = new Label("Hotel Reservation System");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label subtitleLabel = new Label("Welcome to our luxury hotel");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ecf0f1;");
        
        Button searchButton = createStyledButton("Search & Book Rooms");
        searchButton.setOnAction(e -> {
            SearchRoomsView searchView = new SearchRoomsView(stage, roomManager, bookingManager);
            stage.setScene(searchView.createScene());
        });
        
        Button viewBookingsButton = createStyledButton("View My Bookings");
        viewBookingsButton.setOnAction(e -> {
            MyBookingsView bookingsView = new MyBookingsView(stage, roomManager, bookingManager);
            stage.setScene(bookingsView.createScene());
        });
        
        Button exitButton = createStyledButton("Exit");
        exitButton.setOnAction(e -> stage.close());
        exitButton.setStyle(exitButton.getStyle() + "-fx-background-color: #c0392b;");
        
        root.getChildren().addAll(titleLabel, subtitleLabel, searchButton, viewBookingsButton, exitButton);
        
        return new Scene(root, 800, 600);
    }
    
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setMinWidth(250);
        button.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-padding: 15 30 15 30; " +
            "-fx-background-color: #27ae60; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() + "-fx-background-color: #229954;"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle() + "-fx-background-color: #27ae60;"));
        return button;
    }
}