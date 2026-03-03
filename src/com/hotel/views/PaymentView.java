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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PaymentView {
    private Stage stage;
    private RoomManager roomManager;
    private BookingManager bookingManager;
    private Room selectedRoom;
    private String guestName;
    private String phoneNumber;
    private String email;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
    public PaymentView(Stage stage, RoomManager roomManager, BookingManager bookingManager,
                      Room selectedRoom, String guestName, String phoneNumber, String email,
                      LocalDate checkInDate, LocalDate checkOutDate) {
        this.stage = stage;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
        this.selectedRoom = selectedRoom;
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
    
    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Top section
        VBox topSection = new VBox(10);
        topSection.setPadding(new Insets(20));
        topSection.setStyle("-fx-background-color: #34495e;");
        
        Label titleLabel = new Label("Payment");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        topSection.getChildren().add(titleLabel);
        
        // Center section
        VBox centerSection = new VBox(20);
        centerSection.setPadding(new Insets(30));
        centerSection.setAlignment(Pos.TOP_CENTER);
        centerSection.setMaxWidth(600);
        
        // Booking Summary
        VBox summaryBox = new VBox(10);
        summaryBox.setPadding(new Insets(20));
        summaryBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 10;");
        
        Label summaryTitle = new Label("Booking Summary");
        summaryTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalAmount = numberOfNights * selectedRoom.getPricePerNight();
        
        Label guestLabel = new Label("Guest: " + guestName);
        guestLabel.setStyle("-fx-font-size: 14px;");
        
        Label roomLabel = new Label("Room: #" + selectedRoom.getRoomId() + " - " + selectedRoom.getRoomType());
        roomLabel.setStyle("-fx-font-size: 14px;");
        
        Label datesLabel = new Label("Dates: " + checkInDate + " to " + checkOutDate);
        datesLabel.setStyle("-fx-font-size: 14px;");
        
        Label nightsLabel = new Label("Number of Nights: " + numberOfNights);
        nightsLabel.setStyle("-fx-font-size: 14px;");
        
        Label priceLabel = new Label("Price per Night: $" + selectedRoom.getPricePerNight());
        priceLabel.setStyle("-fx-font-size: 14px;");
        
        Separator separator = new Separator();
        
        Label totalLabel = new Label("Total Amount: $" + String.format("%.2f", totalAmount));
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
        
        summaryBox.getChildren().addAll(summaryTitle, guestLabel, roomLabel, datesLabel, 
                                       nightsLabel, priceLabel, separator, totalLabel);
        
        // Payment Method Selection
        VBox paymentBox = new VBox(15);
        paymentBox.setPadding(new Insets(20));
        paymentBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 10;");
        
        Label paymentTitle = new Label("Select Payment Method");
        paymentTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        ToggleGroup paymentGroup = new ToggleGroup();
        
        RadioButton creditCardRadio = new RadioButton("Credit Card");
        creditCardRadio.setToggleGroup(paymentGroup);
        creditCardRadio.setSelected(true);
        creditCardRadio.setStyle("-fx-font-size: 14px;");
        
        RadioButton debitCardRadio = new RadioButton("Debit Card");
        debitCardRadio.setToggleGroup(paymentGroup);
        debitCardRadio.setStyle("-fx-font-size: 14px;");
        
        RadioButton cashRadio = new RadioButton("Cash (Pay at Hotel)");
        cashRadio.setToggleGroup(paymentGroup);
        cashRadio.setStyle("-fx-font-size: 14px;");
        
        paymentBox.getChildren().addAll(paymentTitle, creditCardRadio, debitCardRadio, cashRadio);
        
        // Confirm Button
        Button confirmButton = new Button("Confirm Booking & Pay");
        confirmButton.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-padding: 15 40 15 40; " +
            "-fx-background-color: #27ae60; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5;"
        );
        
        confirmButton.setOnAction(e -> {
            RadioButton selectedPayment = (RadioButton) paymentGroup.getSelectedToggle();
            String paymentMethod = selectedPayment.getText();
            
            String bookingId = bookingManager.createBooking(
                guestName, phoneNumber, email, selectedRoom.getRoomId(),
                checkInDate, checkOutDate, paymentMethod
            );
            
            if (bookingId != null) {
                BookingConfirmationView confirmationView = new BookingConfirmationView(
                    stage, roomManager, bookingManager, bookingId
                );
                stage.setScene(confirmationView.createScene());
            } else {
                showAlert("Booking Failed", "Unable to complete booking. Please try again.", Alert.AlertType.ERROR);
            }
        });
        
        centerSection.getChildren().addAll(summaryBox, paymentBox, confirmButton);
        
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
            BookingFormView bookingForm = new BookingFormView(stage, roomManager, bookingManager, selectedRoom);
            stage.setScene(bookingForm.createScene());
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