import java.util.LinkedList;
import java.util.Queue;

/**
 * Reservation class represents a guest's booking request.
 * Version: 5.0
 */
class BookingReservation {

    private String guestName;
    private String roomType;

    public BookingReservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
    }
}


/**
 * BookingRequestQueue manages incoming booking requests.
 * Uses FIFO ordering to ensure fairness.
 * Version: 5.0
 */
class BookingRequestQueue {

    private Queue<BookingReservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request to queue
    public void addRequest(BookingReservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all pending requests
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        int position = 1;

        for (BookingReservation reservation : requestQueue) {
            System.out.println("\nRequest Position: " + position);
            reservation.displayReservation();
            position++;
        }
    }
}


/**
 * UseCase5BookingRequestQueue
 * Demonstrates booking request intake using FIFO queue.
 * Version: 5.1
 */
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking Management System");
        System.out.println("           Version 5.1");
        System.out.println("=====================================\n");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulated guest booking requests
        BookingReservation r1 = new BookingReservation("Alice", "Single Room");
        BookingReservation r2 = new BookingReservation("Bob", "Double Room");
        BookingReservation r3 = new BookingReservation("Charlie", "Suite Room");

        // Add requests to queue (FIFO order)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\nRequests stored successfully. No rooms allocated yet.");
    }
}
