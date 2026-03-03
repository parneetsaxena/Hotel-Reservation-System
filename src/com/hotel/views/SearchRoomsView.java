package com.hotel.views;

import com.hotel.managers.BookingManager;
import com.hotel.managers.RoomManager;
import com.hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class SearchRoomsView {
    private Stage stage;
    private RoomManager roomManager;
    private BookingManager bookingManager;
    private ListView<Room> roomListView;
    
    public SearchRoomsView(Stage stage, RoomManager roomManager, BookingManager bookingManager) {
        this.stage = stage;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
    }
    
    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Top section
        VBox topSection = new VBox(15);
        topSection.setPadding(new Insets(20));
        topSection.setStyle("-fx-background-color: #34495e;");
        
        Label titleLabel = new Label("Search Available Rooms");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        HBox filterBox = new HBox(15);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        
        Label filterLabel = new Label("Room Type:");
        filterLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        ComboBox<String> roomTypeCombo = new ComboBox<>();
        roomTypeCombo.setItems(FXCollections.observableArrayList("All", "Standard", "Deluxe", "Suite"));
        roomTypeCombo.setValue("All");
        roomTypeCombo.setStyle("-fx-font-size: 14px;");
        
        Button searchButton = new Button("Search");
        searchButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 8 20 8 20; " +
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        
        searchButton.setOnAction(e -> {
            String selectedType = roomTypeCombo.getValue();
            List<Room> filteredRooms;
            if (selectedType.equals("All")) {
                filteredRooms = roomManager.getAvailableRooms();
            } else {
                filteredRooms = roomManager.searchRoomsByType(selectedType);
            }
            roomListView.setItems(FXCollections.observableArrayList(filteredRooms));
        });
        
        filterBox.getChildren().addAll(filterLabel, roomTypeCombo, searchButton);
        topSection.getChildren().addAll(titleLabel, filterBox);
        
        // Center section - Room list
        VBox centerSection = new VBox(10);
        centerSection.setPadding(new Insets(20));
        
        roomListView = new ListView<>();
        roomListView.setStyle("-fx-font-size: 14px;");
        roomListView.setCellFactory(param -> new ListCell<Room>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                if (empty || room == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("Room #%d - %s | $%.2f/night | Capacity: %d guests | Status: %s",
                        room.getRoomId(),
                        room.getRoomType(),
                        room.getPricePerNight(),
                        room.getCapacity(),
                        room.isAvailable() ? "Available" : "Booked"
                    ));
                    setStyle("-fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-width: 0 0 1 0;");
                }
            }
        });
        
        List<Room> availableRooms = roomManager.getAvailableRooms();
        roomListView.setItems(FXCollections.observableArrayList(availableRooms));
        
        Button bookButton = new Button("Book Selected Room");
        bookButton.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-padding: 12 25 12 25; " +
            "-fx-background-color: #27ae60; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        
        bookButton.setOnAction(e -> {
            Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                BookingFormView bookingForm = new BookingFormView(stage, roomManager, bookingManager, selectedRoom);
                stage.setScene(bookingForm.createScene());
            } else {
                showAlert("No Selection", "Please select a room to book.", Alert.AlertType.WARNING);
            }
        });
        
        centerSection.getChildren().addAll(roomListView, bookButton);
        VBox.setVgrow(roomListView, Priority.ALWAYS);
        
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
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}