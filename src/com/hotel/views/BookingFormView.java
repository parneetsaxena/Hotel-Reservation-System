package com.hotel.views;

import com.hotel.managers.BookingManager;
import com.hotel.managers.RoomManager;
import com.hotel.models.Room;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class BookingFormView {
    private Stage stage;
    private RoomManager roomManager;
    private BookingManager bookingManager;
    private Room selectedRoom;
    
    public BookingFormView(Stage stage, RoomManager roomManager, BookingManager bookingManager, Room selectedRoom) {
        this.stage = stage;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
        this.selectedRoom = selectedRoom;
    }
    
    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Top section
        VBox topSection = new VBox(10);
        topSection.setPadding(new Insets(20));
        topSection.setStyle("-fx-background-color: #34495e;");
        
        Label titleLabel = new Label("Book Your Room");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label roomInfoLabel = new Label("Room #" + selectedRoom.getRoomId() + " - " + 
                                       selectedRoom.getRoomType() + " ($" + 
                                       selectedRoom.getPricePerNight() + "/night)");
        roomInfoLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ecf0f1;");
        
        topSection.getChildren().addAll(titleLabel, roomInfoLabel);
        
        // Center section - Form
        VBox centerSection = new VBox(15);
        centerSection.setPadding(new Insets(30));
        centerSection.setAlignment(Pos.TOP_CENTER);
        centerSection.setMaxWidth(500);
        
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(15);
        formGrid.setAlignment(Pos.CENTER);
        
        // Guest Name
        Label nameLabel = new Label("Guest Name:");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 8;");
        
        // Phone Number
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        phoneField.setStyle("-fx-font-size: 14px; -fx-padding: 8;");
        
        // Email
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 8;");
        
        // Check-in Date
        Label checkInLabel = new Label("Check-in Date:");
        checkInLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        DatePicker checkInPicker = new DatePicker();
        checkInPicker.setValue(LocalDate.now().plusDays(1));
        checkInPicker.setStyle("-fx-font-size: 14px;");
        
        // Check-out Date
        Label checkOutLabel = new Label("Check-out Date:");
        checkOutLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        DatePicker checkOutPicker = new DatePicker();
        checkOutPicker.setValue(LocalDate.now().plusDays(2));
        checkOutPicker.setStyle("-fx-font-size: 14px;");
        
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(phoneLabel, 0, 1);
        formGrid.add(phoneField, 1, 1);
        formGrid.add(emailLabel, 0, 2);
        formGrid.add(emailField, 1, 2);
        formGrid.add(checkInLabel, 0, 3);
        formGrid.add(checkInPicker, 1, 3);
        formGrid.add(checkOutLabel, 0, 4);
        formGrid.add(checkOutPicker, 1, 4);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(150);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(250);
        formGrid.getColumnConstraints().addAll(col1, col2);
        
        Button proceedButton = new Button("Proceed to Payment");
        proceedButton.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-padding: 12 30 12 30; " +
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        
        proceedButton.setOnAction(e -> {
            if (validateForm(nameField, phoneField, emailField, checkInPicker, checkOutPicker)) {
                PaymentView paymentView = new PaymentView(
                    stage, roomManager, bookingManager, selectedRoom,
                    nameField.getText(), phoneField.getText(), emailField.getText(),
                    checkInPicker.getValue(), checkOutPicker.getValue()
                );
                stage.setScene(paymentView.createScene());
            }
        });
        
        centerSection.getChildren().addAll(formGrid, proceedButton);
        
        // Bottom section
        HBox bottomSection = new HBox(10);
        bottomSection.setPadding(new Insets(15));
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setStyle("-fx-background-color: #34495e;");
        
        Button backButton = new Button("Back");
        backButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-color: #7f8c8d; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        backButton.setOnAction(e -> {
            SearchRoomsView searchView = new SearchRoomsView(stage, roomManager, bookingManager);
            stage.setScene(searchView.createScene());
        });
        
        bottomSection.getChildren().add(backButton);
        
        root.setTop(topSection);
        root.setCenter(centerSection);
        root.setBottom(bottomSection);
        
        return new Scene(root, 800, 600);
    }
    
    private boolean validateForm(TextField nameField, TextField phoneField, TextField emailField,
                                DatePicker checkInPicker, DatePicker checkOutPicker) {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter your name.", Alert.AlertType.ERROR);
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter your phone number.", Alert.AlertType.ERROR);
            return false;
        }
        if (emailField.getText().trim().isEmpty() || !emailField.getText().contains("@")) {
            showAlert("Validation Error", "Please enter a valid email address.", Alert.AlertType.ERROR);
            return false;
        }
        if (checkInPicker.getValue() == null || checkOutPicker.getValue() == null) {
            showAlert("Validation Error", "Please select check-in and check-out dates.", Alert.AlertType.ERROR);
            return false;
        }
        if (checkInPicker.getValue().isBefore(LocalDate.now())) {
            showAlert("Validation Error", "Check-in date cannot be in the past.", Alert.AlertType.ERROR);
            return false;
        }
        if (checkOutPicker.getValue().isBefore(checkInPicker.getValue().plusDays(1))) {
            showAlert("Validation Error", "Check-out date must be at least one day after check-in.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}