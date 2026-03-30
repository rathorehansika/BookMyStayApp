import java.util.HashMap;
import java.util.Map;

/**
 * RoomInventory
 *
 * This class manages centralized room availability
 * using a HashMap. It acts as the single source of truth
 * for all room inventory data in the system.
 *
 * Version: 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /**
     * Constructor initializes the inventory with
     * default room availability.
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    /**
     * Retrieves current availability for a room type.
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Updates room availability in a controlled way.
     */
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type does not exist in inventory.");
        }
    }

    /**
     * Displays the current inventory state.
     */
    public void displayInventory() {
        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}


/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management
 * using a HashMap for the Book My Stay Hotel Booking System.
 *
 * Version: 3.1
 *
 * @author Oindri
 */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking Management System");
        System.out.println("           Version 3.1");
        System.out.println("======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        System.out.println("\nChecking availability of Double Room...");
        int available = inventory.getAvailability("Double Room");
        System.out.println("Double Room Available: " + available);

        // Update inventory
        System.out.println("\nUpdating availability after booking...");
        inventory.updateAvailability("Double Room", available - 1);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication finished successfully.");
    }
}