import java.util.*;

// Represents a confirmed booking
class BookingRecord {
    private String reservationId;
    private String guestName;
    private String roomId;
    private String roomType;

    public BookingRecord(String reservationId, String guestName, String roomId, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomId = roomId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest Name: " + guestName +
                ", Room ID: " + roomId +
                ", Room Type: " + roomType;
    }
}

// Maintains booking history
class BookingHistory {
    private List<BookingRecord> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addReservation(BookingRecord booking) {
        confirmedBookings.add(booking);
    }

    public boolean removeReservation(String reservationId) {
        return confirmedBookings.removeIf(b -> b.getReservationId().equals(reservationId));
    }

    public BookingRecord getReservation(String reservationId) {
        for (BookingRecord b : confirmedBookings) {
            if (b.getReservationId().equals(reservationId)) return b;
        }
        return null;
    }

    public List<BookingRecord> getAllReservations() {
        return confirmedBookings;
    }
}

// Tracks room inventory per room type
class RoomInventory {
    private Map<String, Integer> roomCount;

    public RoomInventory() {
        roomCount = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        roomCount.put(roomType, count);
    }

    public void increment(String roomType) {
        roomCount.put(roomType, roomCount.getOrDefault(roomType, 0) + 1);
    }

    public void decrement(String roomType) {
        roomCount.put(roomType, roomCount.getOrDefault(roomType, 0) - 1);
    }

    public int getAvailable(String roomType) {
        return roomCount.getOrDefault(roomType, 0);
    }

    public void printInventory() {
        System.out.println("\n--- Room Inventory ---");
        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

// Handles booking cancellations
class CancellationService {
    private BookingHistory history;
    private RoomInventory inventory;
    private Stack<String> rollbackStack;

    public CancellationService(BookingHistory history, RoomInventory inventory) {
        this.history = history;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {
        BookingRecord booking = history.getReservation(reservationId);

        if (booking == null) {
            System.out.println("Cancellation failed: reservation ID " + reservationId + " not found.");
            return;
        }

        // Record room ID for rollback
        rollbackStack.push(booking.getRoomId());

        // Restore inventory count
        inventory.increment(booking.getRoomType());

        // Remove from booking history
        history.removeReservation(reservationId);

        System.out.println("Cancellation successful for reservation ID " + reservationId +
                ". Room " + booking.getRoomId() + " released.");
    }

    // For demonstration: show recently released rooms
    public void showRollbackStack() {
        System.out.println("\nRecently released room IDs (LIFO): " + rollbackStack);
    }
}

// Main class
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        // Initialize booking history and inventory
        BookingHistory history = new BookingHistory();
        RoomInventory inventory = new RoomInventory();

        // Add room types
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 3);

        // Create some bookings
        BookingRecord b1 = new BookingRecord("RES201", "Alice", "S101", "Single");
        BookingRecord b2 = new BookingRecord("RES202", "Bob", "D201", "Double");
        BookingRecord b3 = new BookingRecord("RES203", "Charlie", "S102", "Single");

        // Add to history and decrement inventory
        history.addReservation(b1); inventory.decrement(b1.getRoomType());
        history.addReservation(b2); inventory.decrement(b2.getRoomType());
        history.addReservation(b3); inventory.decrement(b3.getRoomType());

        // Print current inventory
        inventory.printInventory();

        // Initialize cancellation service
        CancellationService cancelService = new CancellationService(history, inventory);

        // Cancel a booking
        cancelService.cancelBooking("RES202");  // Bob's booking
        cancelService.cancelBooking("RES999");  // Non-existent booking

        // Show updated inventory
        inventory.printInventory();

        // Show rollback stack
        cancelService.showRollbackStack();

        // Print remaining bookings
        System.out.println("\n--- Remaining Bookings ---");
        for (BookingRecord b : history.getAllReservations()) {
            System.out.println(b);
        }
    }
}