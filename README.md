# Hotel Reservation System

A comprehensive hotel reservation system built with **Core Java** and **JavaFX** GUI.

## Features

### Main Features
- **Search & Book Rooms**: Browse available rooms by type (Standard, Deluxe, Suite)
- **Room Categorization**: Three room categories with different pricing
  - Standard: $100/night (2 guests)
  - Deluxe: $200/night (3 guests)
  - Suite: $350/night (4 guests)
- **Booking Management**: Create and cancel reservations easily
- **Payment Simulation**: Multiple payment methods (Credit Card, Debit Card, Cash)
- **Booking Details**: View complete booking information and history
- **Data Persistence**: File I/O for storing rooms and bookings

### Technical Features
- **Object-Oriented Design**: Clean architecture with separation of concerns
- **JavaFX GUI**: Modern, intuitive user interface
- **File-based Storage**: Persistent data storage using text files
- **Real-time Availability**: Automatic room availability updates
- **Input Validation**: Form validation for booking details
- **Booking ID Generation**: Unique booking reference numbers

## System Requirements

- **Java Development Kit (JDK)**: Version 11 or higher
- **JavaFX SDK**: Version 17.0.2 (included in project)
- **Operating System**: Windows, macOS, or Linux

## Project Structure

```
hotel-reservation-system/
├── src/
│   └── com/hotel/
│       ├── models/
│       │   ├── Room.java
│       │   └── Booking.java
│       ├── managers/
│       │   ├── RoomManager.java
│       │   └── BookingManager.java
│       ├── utils/
│       │   └── FileManager.java
│       ├── views/
│       │   ├── HomePage.java
│       │   ├── SearchRoomsView.java
│       │   ├── BookingFormView.java
│       │   ├── PaymentView.java
│       │   ├── BookingConfirmationView.java
│       │   └── MyBookingsView.java
│       └── HotelReservationApp.java
├── bin/                    # Compiled classes
├── data/                   # Data storage
│   ├── rooms.txt          # Room data
│   └── bookings.txt       # Booking data
├── javafx-sdk/            # JavaFX SDK
├── compile.sh             # Linux/Mac compile script
├── compile.bat            # Windows compile script
├── run.sh                 # Linux/Mac run script
├── run.bat                # Windows run script
└── README.md              # This file
```

## Installation & Setup

### Prerequisites
Ensure Java JDK 11+ is installed on your system:
```bash
java -version
```

### Option 1: Using Pre-compiled Application

**For Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

**For Windows:**
```
run.bat
```

### Option 2: Compile from Source

**For Linux/Mac:**
```bash
chmod +x compile.sh
./compile.sh
./run.sh
```

**For Windows:**
```
compile.bat
run.bat
```

## How to Use

### 1. Home Page
- Launch the application to see the main menu
- Choose from:
  - **Search & Book Rooms**: Find and book available rooms
  - **View My Bookings**: See all your bookings
  - **Exit**: Close the application

### 2. Searching for Rooms
- Select room type filter (All, Standard, Deluxe, Suite)
- Click "Search" to view available rooms
- View room details: Room number, type, price, capacity, availability
- Select a room and click "Book Selected Room"

### 3. Making a Booking
- Fill in guest details:
  - Full name
  - Phone number
  - Email address
  - Check-in date
  - Check-out date
- Click "Proceed to Payment"

### 4. Payment
- Review booking summary
- View total amount calculation
- Select payment method:
  - Credit Card
  - Debit Card
  - Cash (Pay at Hotel)
- Click "Confirm Booking & Pay"
- Receive booking confirmation with unique Booking ID

### 5. Managing Bookings
- Go to "View My Bookings" from home page
- See list of all bookings (confirmed and cancelled)
- Select a booking to:
  - **View Details**: See complete booking information
  - **Cancel Booking**: Cancel a confirmed booking

## Data Storage

The system stores data in text files:

- **rooms.txt**: Contains room information
  - Format: `roomId,roomType,pricePerNight,isAvailable,capacity`
  
- **bookings.txt**: Contains booking records
  - Format: `bookingId|guestName|phone|email|roomId|roomType|checkIn|checkOut|amount|payment|status|bookingDate`

## OOP Concepts Used

- **Encapsulation**: Private fields with public getters/setters
- **Abstraction**: Separation of data models, managers, and views
- **Inheritance**: JavaFX component inheritance
- **Modularity**: Clean separation of concerns across packages

## Default Room Inventory

The system initializes with 12 rooms:
- 5 Standard rooms (101-105): $100/night
- 4 Deluxe rooms (201-204): $200/night
- 3 Suite rooms (301-303): $350/night

## Troubleshooting

### Application won't start
- Ensure Java 11+ is installed
- Check that JavaFX SDK is in the `javafx-sdk` directory
- Verify scripts have execution permissions (Linux/Mac)

### Compilation errors
- Ensure you're using JDK (not JRE)
- Check JavaFX module path in compile scripts

### Data not persisting
- Ensure the `data/` directory exists
- Check file permissions for read/write access

## Future Enhancements

- User authentication system
- Admin panel for hotel management
- Room images and detailed amenities
- Search by date availability
- Email confirmation for bookings
- Database integration (MySQL/PostgreSQL)
- Online payment gateway integration
- Customer reviews and ratings

## Developer Information

**Project**: Hotel Reservation System  
**Language**: Java 17  
**GUI Framework**: JavaFX 17.0.2  
**Architecture**: Model-Manager-View pattern  
**Storage**: File I/O (Text files)  

## License

This project is created for educational purposes.

---

**Note**: This is a simulation system. Payment processing is simulated and no real transactions occur.
