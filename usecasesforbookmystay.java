package bookmystay;

import java.util.*;

// Class representing an Add-On Service
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + price + ")";
    }
}

// Class representing a Reservation
class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Manager class for Add-On Services
class AddOnServiceManager {

    // Map: Reservation ID -> List of Add-On Services
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {
        double total = 0.0;

        List<AddOnService> services = serviceMap.get(reservationId);
        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nAdd-On Services for Reservation ID: " + reservationId);
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }
    }
}

// Main Class
public class usecasesforbookmystay {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Sample reservation (core booking untouched)
        Reservation reservation = new Reservation("R101", "Nik");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Predefined services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);

        int choice;

        do {
            System.out.println("\n===== Add-On Service Menu =====");
            System.out.println("1. Add WiFi");
            System.out.println("2. Add Breakfast");
            System.out.println("3. Add Spa");
            System.out.println("4. Add Airport Pickup");
            System.out.println("5. View Services");
            System.out.println("6. View Total Cost");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservation.getReservationId(), wifi);
                    break;

                case 2:
                    manager.addService(reservation.getReservationId(), breakfast);
                    break;

                case 3:
                    manager.addService(reservation.getReservationId(), spa);
                    break;

                case 4:
                    manager.addService(reservation.getReservationId(), airportPickup);
                    break;

                case 5:
                    manager.displayServices(reservation.getReservationId());
                    break;

                case 6:
                    double total = manager.calculateTotalCost(reservation.getReservationId());
                    System.out.println("Total Add-On Cost: ₹" + total);
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