package bookmystay;

import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public boolean isCancelled() { return isCancelled; }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Room ID: " + roomId +
                ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
    }
}

// Booking Service
class BookingService {

    private Map<String, Integer> inventory;
    private Map<String, Reservation> bookings;
    private Stack<String> rollbackStack;

    public BookingService() {
        inventory = new HashMap<>();
        bookings = new HashMap<>();
        rollbackStack = new Stack<>();

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    // Generate room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 1).toUpperCase() + (int)(Math.random() * 100);
    }

    // Confirm booking
    public void confirmBooking(String id, String name, String roomType) {
        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            System.out.println("Booking failed: No availability.");
            return;
        }

        String roomId = generateRoomId(roomType);
        inventory.put(roomType, inventory.get(roomType) - 1);

        Reservation res = new Reservation(id, name, roomType, roomId);
        bookings.put(id, res);

        System.out.println("Booking Confirmed!");
        System.out.println(res);
    }

    // Cancel booking with rollback
    public void cancelBooking(String reservationId) throws CancellationException {

        // Validation
        if (!bookings.containsKey(reservationId)) {
            throw new CancellationException("Reservation does not exist!");
        }

        Reservation res = bookings.get(reservationId);

        if (res.isCancelled()) {
            throw new CancellationException("Booking already cancelled!");
        }

        // Step 1: Push room ID to stack (rollback tracking)
        rollbackStack.push(res.getRoomId());

        // Step 2: Restore inventory
        String roomType = res.getRoomType();
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Step 3: Mark booking cancelled
        res.cancel();

        System.out.println("Cancellation Successful!");
        System.out.println("Rolled back Room ID: " + rollbackStack.peek());
    }

    // Display bookings
    public void displayBookings() {
        System.out.println("\n===== All Bookings =====");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\n===== Inventory =====");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
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
            System.out.println("1. Confirm Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. View Inventory");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Suite): ");
                    String room = sc.nextLine();

                    service.confirmBooking(id, name, room);
                    break;

                case 2:
                    try {
                        System.out.print("Enter Reservation ID to cancel: ");
                        String cancelId = sc.nextLine();

                        service.cancelBooking(cancelId);

                    } catch (CancellationException e) {
                        System.out.println("Cancellation Failed: " + e.getMessage());
                    }
                    break;

                case 3:
                    service.displayBookings();
                    break;

                case 4:
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