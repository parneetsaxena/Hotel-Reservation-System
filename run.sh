#!/bin/bash
# Run script for Hotel Reservation System

cd "$(dirname "$0")"

java --module-path javafx-sdk/lib --add-modules javafx.controls -cp bin com.hotel.HotelReservationApp
