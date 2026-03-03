#!/bin/bash
# Compile script for Hotel Reservation System

cd "$(dirname "$0")"

echo "Compiling Hotel Reservation System..."

mkdir -p bin

javac --module-path javafx-sdk/lib --add-modules javafx.controls -d bin -sourcepath src src/com/hotel/HotelReservationApp.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Run the application using: ./run.sh"
else
    echo "Compilation failed!"
    exit 1
fi
