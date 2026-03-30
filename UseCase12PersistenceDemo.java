import java.io.*;
import java.util.*;

public class UseCase12PersistenceDemo {

    private static final String BOOKINGS_FILE = "bookings.dat";
    private static final String INVENTORY_FILE = "inventory.dat";

    public static void main(String[] args) {
        // Initialize inventory and bookings
        Map<String, Integer> inventory = new HashMap<>();
        List<Reservation> bookings = new ArrayList<>();

        // Load persisted data if available
        loadInventory(inventory);
        loadBookings(bookings);

        // Display current state
        System.out.println("Current Inventory: " + inventory);
        System.out.println("Current Bookings: " + bookings);

        // Create a new reservation
        Reservation newRes = new Reservation("Alice", "SingleRoom", 2);

        // Check availability
        String roomKey = newRes.getRoomType();
        int available = inventory.getOrDefault(roomKey, 5); // default 5 if not in map
        if (available > 0) {
            bookings.add(newRes);
            inventory.put(roomKey, available - 1);
            System.out.println("\nBooking confirmed: " + newRes);
            System.out.println("Updated Inventory: " + inventory);
        } else {
            System.out.println("\nNo rooms available for type: " + roomKey);
        }

        // Persist current state
        saveInventory(inventory);
        saveBookings(bookings);
    }

    // ---------------- Persistence Methods ----------------

    private static void saveInventory(Map<String, Integer> inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(inventory);
            System.out.println("\nInventory saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static void loadInventory(Map<String, Integer> inventory) {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<?, ?> loaded = (Map<?, ?>) ois.readObject();
            // Ensure all values are integers
            for (Map.Entry<?, ?> entry : loaded.entrySet()) {
                inventory.put(entry.getKey().toString(), Integer.parseInt(entry.getValue().toString()));
            }
            System.out.println("\nInventory loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }

    private static void saveBookings(List<Reservation> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
            System.out.println("Bookings saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }

    private static void loadBookings(List<Reservation> bookings) {
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<?> loaded = (List<?>) ois.readObject();
            for (Object obj : loaded) {
                if (obj instanceof Reservation) {
                    bookings.add((Reservation) obj);
                }
            }
            System.out.println("Bookings loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
    }
}