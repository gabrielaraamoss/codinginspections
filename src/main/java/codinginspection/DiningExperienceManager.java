package codinginspection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class DiningExperienceManager {
    public static void main(String[] args) {
        Map<String, Double> menu = createMenu(); 
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Dining Experience Manager!");
        System.out.println("Please select your meals and quantities. Type 'done' when you're finished.");

        Map<String, Integer> order = new HashMap<>();
        double totalCost = 0;

        while (true) {
            System.out.print("Enter meal name: ");
            String meal = scanner.nextLine().toLowerCase();

            if (meal.equals("done")) {
                break;
            }

            if (menu.containsKey(meal)) {
                System.out.print("Enter quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                if (quantity > 0) {
                    order.put(meal, quantity);
                    totalCost += menu.get(meal) * quantity;
                } else {
                    System.out.println("Quantity must be a positive integer greater than zero.");
                }
            } else {
                System.out.println("Invalid meal selection. Please choose a valid meal from the menu.");
            }
        }

        // Calculate discounts
        if (order.size() > 5) {
            totalCost *= 0.9; // 10% discount
        }
        if (order.size() > 10) {
            totalCost *= 0.8; // 20% discount
        }
        if (totalCost > 100) {
            totalCost -= 25; // $25 discount
        } else if (totalCost > 50) {
            totalCost -= 10; // $10 discount
        }

        // Display order summary and confirm
        System.out.println("Selected meals and quantities:");
        for (Map.Entry<String, Integer> entry : order.entrySet()) {
            System.out.println(entry.getKey() + " x" + entry.getValue());
        }
        System.out.println("Total cost: $" + totalCost);

        System.out.print("Confirm your order (yes/no): ");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            System.out.println("Order confirmed. Total cost: $" + (int) totalCost);
        } else {
            System.out.println("Order canceled.");
        }
    }

    // Create a menu with meal items and prices
    private static Map<String, Double> createMenu() {
        Map<String, Double> menu = new HashMap<>();
        // Add meal items and prices here
        menu.put("burger", 10.0);
        menu.put("pizza", 15.0);
        menu.put("pasta", 12.0);
        return menu;
    }
}