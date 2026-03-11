package bookmystay;

import java.util.*;

public class usecasesforbookmystay {

    // Queue to store booking requests (FIFO)
    private Queue<String> bookingQueue = new LinkedList<>();

    // Inventory of room types
    private Map<String, Integer> inventory = new HashMap<>();

    // Mapping room type -> allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Set to ensure unique room IDs
    private Set<String> usedRoomIds = new HashSet<>();

    // Constructor to initialize inventory
    public usecasesforbookmystay() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    // Add booking request
    public void addBookingRequest(String roomType) {
        bookingQueue.offer(roomType);
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0,1).toUpperCase() + (100 + new Random().nextInt(900));
        } while (usedRoomIds.contains(roomId));

        usedRoomIds.add(roomId);
        return roomId;
    }

    // Process booking requests
    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            String roomType = bookingQueue.poll(); // FIFO

            if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRooms.get(roomType).add(roomId);

                // update inventory immediately
                inventory.put(roomType, inventory.get(roomType) - 1);

                System.out.println("Reservation Confirmed → "
                        + roomType + " Room Allocated: " + roomId);

            } else {
                System.out.println("Reservation Failed → No " + roomType + " rooms available");
            }
        }
    }

    // Display final allocation
    public void displayAllocations() {
        System.out.println("\nFinal Room Allocations:");

        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " : " + allocatedRooms.get(type));
        }
    }

    public static void main(String[] args) {

        usecasesforbookmystay service = new usecasesforbookmystay();

        // Sample booking requests
        service.addBookingRequest("Single");
        service.addBookingRequest("Double");
        service.addBookingRequest("Suite");
        service.addBookingRequest("Single");
        service.addBookingRequest("Double");

        // Process requests
        service.processBookings();

        // Show allocations
        service.displayAllocations();
    }
}