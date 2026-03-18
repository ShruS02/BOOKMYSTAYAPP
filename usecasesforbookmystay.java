package bookmystay;

import java.util.*;

// Reservation Class (Core Booking Entity)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Price: ₹" + price;
    }
}

// Booking History Class (Stores confirmed bookings)
class BookingHistory {

    private List<Reservation> historyList;

    public BookingHistory() {
        historyList = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        historyList.add(reservation);
        System.out.println("Reservation added to history: " + reservation.getReservationId());
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return historyList;
    }
}

// Reporting Service Class
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n===== Booking History =====");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("\n===== Booking Summary Report =====");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }

    // Report by Room Type
    public void reportByRoomType(List<Reservation> reservations) {
        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\n===== Bookings by Room Type =====");
        for (String type : roomCount.keySet()) {
            System.out.println(type + ": " + roomCount.get(type));
        }
    }
}

// Main Class
public class usecasesforbookmystay {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        int choice;

        do {
            System.out.println("\n===== Booking System Menu =====");
            System.out.println("1. Confirm Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Summary Report");
            System.out.println("4. Report by Room Type");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String room = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    Reservation res = new Reservation(id, name, room, price);

                    // Add to history (only after confirmation)
                    history.addReservation(res);
                    break;

                case 2:
                    reportService.displayAllBookings(history.getAllReservations());
                    break;

                case 3:
                    reportService.generateSummary(history.getAllReservations());
                    break;

                case 4:
                    reportService.reportByRoomType(history.getAllReservations());
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