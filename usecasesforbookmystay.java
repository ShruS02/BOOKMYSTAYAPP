/**
 * UseCase2RoomInitialization
 *
 * Demonstrates object modeling using abstraction,
 * inheritance, encapsulation, and polymorphism.
 *
 * This version introduces predefined room types
 * with static availability representation.
 *
 * @author YourName
 * @version 2.1
 */

// Abstract class representing a generalized Room
abstract class Room {

    private String roomType;
    private int numberOfBeds;
    private double roomSize;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double roomSize, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getRoomSize() {
        return roomSize;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    // Common method shared across all room types
    public void displayRoomDetails() {
        System.out.println("Room Type      : " + roomType);
        System.out.println("Beds           : " + numberOfBeds);
        System.out.println("Size (sq ft)   : " + roomSize);
        System.out.println("Price/Night ($): " + pricePerNight);
    }
}

// Concrete Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 180.0, 80.0);
    }
}

// Concrete Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 250.0, 140.0);
    }
}

// Concrete Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 450.0, 300.0);
    }
}

public class usecasesforbookmystay {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println(" Welcome to Book My Stay");
        System.out.println(" Hotel Booking System v2.1");
        System.out.println("=========================================\n");

        // Polymorphism: Referencing concrete rooms using Room type
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display Single Room
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailability);
        System.out.println("-----------------------------------------\n");

        // Display Double Room
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailability);
        System.out.println("-----------------------------------------\n");

        // Display Suite Room
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailability);
        System.out.println("-----------------------------------------\n");

        // Application terminates (linear execution)
    }
}