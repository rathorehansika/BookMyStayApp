import java.util.*;
import java.util.concurrent.*;

// Represents a booking request
class BookingRequest {
    private String reservationId;
    private String guestName;
    private String roomType;

    public BookingRequest(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Manages inventory with thread-safe updates
class RoomInventory {
    private Map<String, Integer> roomCount;

    public RoomInventory() {
        roomCount = new HashMap<>();
    }

    public synchronized void addRoomType(String roomType, int count) {
        roomCount.put(roomType, count);
    }

    public synchronized boolean allocateRoom(String roomType) {
        int available = roomCount.getOrDefault(roomType, 0);
        if (available > 0) {
            roomCount.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void printInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

// Maintains confirmed bookings in thread-safe manner
class BookingHistory {
    private List<BookingRequest> confirmedBookings = Collections.synchronizedList(new ArrayList<>());

    public void addBooking(BookingRequest booking) {
        confirmedBookings.add(booking);
    }

    public void printConfirmedBookings() {
        System.out.println("\n--- Confirmed Bookings ---");
        synchronized (confirmedBookings) { // iterate safely
            for (BookingRequest b : confirmedBookings) {
                System.out.println("Reservation ID: " + b.getReservationId() +
                        ", Guest Name: " + b.getGuestName() +
                        ", Room Type: " + b.getRoomType());
            }
        }
    }
}

// Processes booking requests concurrently
class ConcurrentBookingProcessor implements Runnable {
    private BookingRequest request;
    private RoomInventory inventory;
    private BookingHistory history;

    public ConcurrentBookingProcessor(BookingRequest request, RoomInventory inventory, BookingHistory history) {
        this.request = request;
        this.inventory = inventory;
        this.history = history;
    }

    @Override
    public void run() {
        synchronized (inventory) { // critical section for inventory allocation
            boolean allocated = inventory.allocateRoom(request.getRoomType());
            if (allocated) {
                history.addBooking(request);
                System.out.println("Booking successful: " + request.getReservationId() + " for " + request.getGuestName());
            } else {
                System.out.println("Booking failed (no available rooms): " + request.getReservationId() + " for " + request.getGuestName());
            }
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Initialize inventory
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        // Create a list of booking requests (simulate multiple guests)
        List<BookingRequest> requests = Arrays.asList(
                new BookingRequest("RES401", "Alice", "Single"),
                new BookingRequest("RES402", "Bob", "Single"),
                new BookingRequest("RES403", "Charlie", "Double"),
                new BookingRequest("RES404", "David", "Single"),   // should fail if single rooms exhausted
                new BookingRequest("RES405", "Eve", "Double")      // should fail
        );

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit requests concurrently
        for (BookingRequest req : requests) {
            executor.submit(new ConcurrentBookingProcessor(req, inventory, history));
        }

        // Shutdown executor and wait for completion
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Show final state
        inventory.printInventory();
        history.printConfirmedBookings();
    }
}