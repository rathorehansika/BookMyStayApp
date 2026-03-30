import java.util.HashMap;

/**
 * Abstract class representing a hotel room.
 * Version: 4.0
 */
abstract class HotelRoom {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public HotelRoom(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : ₹" + price + " per night");
    }
}


/* -------- ROOM TYPES -------- */

class HotelSingleRoom extends HotelRoom {

    public HotelSingleRoom() {
        super("Single Room", 1, 200, 2500);
    }
}

class HotelDoubleRoom extends HotelRoom {

    public HotelDoubleRoom() {
        super("Double Room", 2, 350, 4000);
    }
}

class HotelSuiteRoom extends HotelRoom {

    public HotelSuiteRoom() {
        super("Suite Room", 3, 600, 8000);
    }
}


/* -------- INVENTORY -------- */

class HotelRoomInventory {

    private HashMap<String, Integer> inventory;

    public HotelRoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 0); // unavailable example
        inventory.put("Suite Room", 3);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


/* -------- SEARCH SERVICE -------- */

class HotelRoomSearchService {

    private HotelRoomInventory inventory;

    public HotelRoomSearchService(HotelRoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(HotelRoom[] rooms) {

        System.out.println("Available Rooms:\n");

        for (HotelRoom room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms : " + available);
                System.out.println("-----------------------------");
            }
        }
    }
}


/* -------- MAIN APPLICATION -------- */

/**
 * UseCase4RoomSearch
 * Version 4.1
 */
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking Management System");
        System.out.println("           Version 4.1");
        System.out.println("=====================================\n");

        // Initialize inventory
        HotelRoomInventory inventory = new HotelRoomInventory();

        // Create room objects
        HotelRoom[] rooms = {
                new HotelSingleRoom(),
                new HotelDoubleRoom(),
                new HotelSuiteRoom()
        };

        // Search service
        HotelRoomSearchService searchService = new HotelRoomSearchService(inventory);

        // Perform search
        searchService.searchAvailableRooms(rooms);

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}