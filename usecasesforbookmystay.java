package bookmystay;

import java.io.*;
import java.util.*;

// Reservation Class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType;
    }
}

// Wrapper class to persist system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully!");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with clean state.");
        }
        return null;
    }
}

// Booking System
class BookingSystem {

    private Map<String, Integer> inventory;
    private List<Reservation> bookings;

    public BookingSystem() {

        // Try loading previous state
        SystemState state = PersistenceService.load();

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            inventory = new HashMap<>();
            bookings = new ArrayList<>();

            inventory.put("Single", 2);
            inventory.put("Double", 2);
            inventory.put("Suite", 1);
        }
    }

    public void bookRoom(String id, String name, String roomType) {

        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            System.out.println("Booking failed: No availability.");
            return;
        }

        inventory.put(roomType, inventory.get(roomType) - 1);
        Reservation res = new Reservation(id, name, roomType);
        bookings.add(res);

        System.out.println("Booking successful!");
    }

    public void displayBookings() {
        System.out.println("\n===== Booking History =====");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
    }

    public void displayInventory() {
        System.out.println("\n===== Inventory =====");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }

    public void shutdown() {
        SystemState state = new SystemState(inventory, bookings);
        PersistenceService.save(state);
    }
}

// Main Class
public class usecasesforbookmystay {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        int choice;

        do {
            System.out.println("\n===== Booking Menu =====");
            System.out.println("1. Book Room");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("0. Exit (Save State)");
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

                    system.bookRoom(id, name, room);
                    break;

                case 2:
                    system.displayBookings();
                    break;

                case 3:
                    system.displayInventory();
                    break;

                case 0:
                    system.shutdown();
                    System.out.println("System shutting down...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}