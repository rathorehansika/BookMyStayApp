/**
 * UseCase2RoomInitialization
 *
 * This program demonstrates basic room type modeling
 * for the Book My Stay Hotel Booking System.
 *
 * It introduces abstraction, inheritance, and polymorphism
 * to represent different types of hotel rooms while storing
 * availability using simple variables.
 *
 * Version: 2.1
 *
 * @author Oindri
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : ₹" + price + " per night");
    }
}


/* ---------------- SINGLE ROOM ---------------- */

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 2500);
    }
}


/* ---------------- DOUBLE ROOM ---------------- */

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 4000);
    }
}


/* ---------------- SUITE ROOM ---------------- */

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 8000);
    }
}


/* ---------------- MAIN APPLICATION ---------------- */

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking Management System");
        System.out.println("           Version 2.1");
        System.out.println("=====================================\n");

        /* Create Room Objects */
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        /* Static availability variables */
        int singleRoomAvailable = 10;
        int doubleRoomAvailable = 6;
        int suiteRoomAvailable = 3;

        /* Display Single Room Details */
        System.out.println("----- Single Room -----");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + singleRoomAvailable);
        System.out.println();

        /* Display Double Room Details */
        System.out.println("----- Double Room -----");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleRoomAvailable);
        System.out.println();

        /* Display Suite Room Details */
        System.out.println("----- Suite Room -----");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteRoomAvailable);
        System.out.println();

        System.out.println("Application finished successfully.");
    }
}