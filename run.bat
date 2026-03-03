@echo off
REM Run script for Hotel Reservation System (Windows)

cd /d %~dp0

java --module-path javafx-sdk/lib --add-modules javafx.controls -cp bin com.hotel.HotelReservationApp

pause
