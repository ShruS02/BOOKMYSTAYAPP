import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean updateAvailability(String roomType, int change) {
        if (!inventory.containsKey(roomType)) return false;

        int current = inventory.get(roomType);
        int updated = current + change;

        if (updated < 0) return false;

        inventory.put(roomType, updated);
        return true;
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
        System.out.println();
    }
}

public class usecasesforbookmystay {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v3.1\n");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        inventory.updateAvailability("Single Room", -1);

        inventory.displayInventory();
    }
}