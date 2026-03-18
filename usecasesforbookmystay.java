package bookmystay;

import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType;
    }
}

// Validator Class
class InvalidBookingValidator {

    private static final List<String> VALID_ROOM_TYPES =
            Arrays.asList("Single", "Double", "Suite");

    // Validate booking input and inventory
    public static void validate(String roomType, Map<String, Integer> inventory)
            throws InvalidBookingException {

        // Validate room type
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected!");
        }

        // Validate availability
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Room type not found in inventory!");
        }

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type!");
        }
    }
}

// Booking Service
class BookingService {

    private Map<String, Integer> inventory;

    public BookingService() {
        inventory = new HashMap<>();

        // Initial inventory
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }

    public Reservation confirmBooking(String id, String name, String roomType)
            throws InvalidBookingException {

        // Fail-fast validation
        InvalidBookingValidator.validate(roomType, inventory);

        // Safe state update
        inventory.put(roomType, inventory.get(roomType) - 1);

        return new Reservation(id, name, roomType);
    }
}

// Main Class
public class usecasesforbookmystay {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingService service = new BookingService();

        int choice;

        do {
            System.out.println("\n===== Booking Menu =====");
            System.out.println("1. Book Room");
            System.out.println("2. View Inventory");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter Reservation ID: ");
                        String id = sc.nextLine();

                        System.out.print("Enter Guest Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Room Type (Single/Double/Suite): ");
                        String roomType = sc.nextLine();

                        Reservation res = service.confirmBooking(id, name, roomType);

                        System.out.println("\nBooking Successful!");
                        System.out.println(res);

                    } catch (InvalidBookingException e) {
                        // Graceful failure handling
                        System.out.println("Booking Failed: " + e.getMessage());
                    }
                    break;

                case 2:
                    service.displayInventory();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}