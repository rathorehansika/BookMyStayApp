import java.util.*;

// Custom exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Represents a booking record
class BookingRecord {
    private String reservationId;
    private String guestName;
    private String roomType;

    public BookingRecord(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest Name: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Validates bookings and inventory
class InvalidBookingValidator {
    private Map<String, Integer> inventory;

    public InvalidBookingValidator(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    // Validates a booking request
    public void validateBooking(String guestName, String roomType) throws InvalidBookingException {
        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Room type '" + roomType + "' does not exist.");
        }

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No available rooms of type '" + roomType + "'.");
        }
    }

    // Decrement inventory safely after validation
    public void allocateRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("\n--- Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

// Main class demonstrating validation and error handling
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {

        // Initialize inventory
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);

        InvalidBookingValidator validator = new InvalidBookingValidator(inventory);
        List<BookingRecord> confirmedBookings = new ArrayList<>();

        // Sample bookings (including invalid cases)
        String[][] bookingRequests = {
                {"RES301", "Alice", "Single"},
                {"RES302", "", "Double"},          // Invalid guest name
                {"RES303", "Bob", "Suite"},        // Invalid room type
                {"RES304", "Charlie", "Double"},   // Valid
                {"RES305", "Dave", "Double"}       // Exceed inventory
        };

        for (String[] request : bookingRequests) {
            String resId = request[0];
            String guestName = request[1];
            String roomType = request[2];

            try {
                validator.validateBooking(guestName, roomType);
                validator.allocateRoom(roomType);
                BookingRecord booking = new BookingRecord(resId, guestName, roomType);
                confirmedBookings.add(booking);
                System.out.println("Booking successful: " + booking);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed for " + resId + ": " + e.getMessage());
            }
        }

        // Show final inventory
        validator.printInventory();

        // Show confirmed bookings
        System.out.println("\n--- Confirmed Bookings ---");
        for (BookingRecord booking : confirmedBookings) {
            System.out.println(booking);
        }
    }
}