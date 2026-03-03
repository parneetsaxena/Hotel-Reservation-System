@echo off
REM Compile script for Hotel Reservation System (Windows)

cd /d %~dp0

echo Compiling Hotel Reservation System...

if not exist bin mkdir bin

javac --module-path javafx-sdk/lib --add-modules javafx.controls -d bin -sourcepath src src/com/hotel/HotelReservationApp.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo Run the application using: run.bat
) else (
    echo Compilation failed!
    pause
    exit /b 1
)

pause
