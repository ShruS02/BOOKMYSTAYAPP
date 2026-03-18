package bookmystay;

import java.util.*;

// Booking Request Class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System
class BookingSystem {

    private Map<String, Integer> inventory;

    public BookingSystem() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    // Synchronized critical section
    public synchronized void allocateRoom(BookingRequest request) {

        String roomType = request.roomType;

        System.out.println(Thread.currentThread().getName() +
                " processing request for " + request.guestName);

        if (inventory.getOrDefault(roomType, 0) > 0) {

            // Simulate delay (to expose race conditions if unsynchronized)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking SUCCESS for " + request.guestName +
                    " | Room: " + roomType +
                    " | Remaining: " + inventory.get(roomType));

        } else {
            System.out.println("Booking FAILED for " + request.guestName +
                    " | No " + roomType + " rooms available");
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    private Queue<BookingRequest> queue;
    private BookingSystem system;

    public BookingProcessor(Queue<BookingRequest> queue, BookingSystem system) {
        this.queue = queue;
        this.system = system;
    }

    @Override
    public void run() {

        while (true) {
            BookingRequest request;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                request = queue.poll();
            }

            // Process booking
            system.allocateRoom(request);
        }
    }
}

// Main Class
public class usecasesforbookmystay {

    public static void main(String[] args) {

        // Shared Queue
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        // Add requests (simulate multiple users)
        bookingQueue.add(new BookingRequest("Alice", "Single"));
        bookingQueue.add(new BookingRequest("Bob", "Single"));
        bookingQueue.add(new BookingRequest("Charlie", "Single")); // should fail

        bookingQueue.add(new BookingRequest("David", "Double"));
        bookingQueue.add(new BookingRequest("Eve", "Double"));

        bookingQueue.add(new BookingRequest("Frank", "Suite"));
        bookingQueue.add(new BookingRequest("Grace", "Suite")); // should fail

        BookingSystem system = new BookingSystem();

        // Create multiple threads
        Thread t1 = new BookingProcessor(bookingQueue, system);
        Thread t2 = new BookingProcessor(bookingQueue, system);
        Thread t3 = new BookingProcessor(bookingQueue, system);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory check
        system.displayInventory();
    }
}