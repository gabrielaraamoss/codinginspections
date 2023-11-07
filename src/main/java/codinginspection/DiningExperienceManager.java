package codinginspection;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Locale;

/**
 * The DiningExperienceManager class manages the dining experience.
 * It allows users to select meals and enter quantities.
 */
public final class DiningExperienceManager {
    
    /**
     * Yes word
     */
    private static final String YES = "yes";
    /**
     * Maximum order size for discount
     */
    private static final int MAX_ORDER_SIZE = 10;

    /**
     * Minimum order size for discount
     */
    private static final int MIN_ORDER_SIZE = 5;

    /**
     * Total cost threshold for discount
     */
    private static final double HIGH_TOTAL_COST = 100.0;

    /**
     * Total cost threshold for discount
     */
    private static final double LOW_TOTAL_COST = 50.0;

    /**
     * Discount for large orders
     */
    private static final double HIGH_DISCOUNT = 0.8;

    /**
     * Discount for medium-sized orders
     */
    private static final double LOW_DISCOUNT = 0.9;


    private DiningExperienceManager() {
    }

    /**
     * The main method for managing the dining experience.
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        final Map<String, Double> menu = createMenu();
        final Logger log = Logger.getLogger(DiningExperienceManager.class.getName());

        try (Scanner scanner = new Scanner(System.in)) {
            displayWelcomeMessage(log);
            final Map<String, Integer> order = createOrder(scanner, menu, log);
            final double totalCost = calculateTotalCost(order, menu);
            displayOrderSummary(order, totalCost, log);
            confirmOrder(scanner, log, totalCost);

        }
    }
    
    
    private static void  displayWelcomeMessage(final Logger log) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Welcome to the Dining Experience Manager!");
            log.fine("Please select your meals and quantities. Type 'done' when you're finished.");
        }
    }
    
    private static Map<String, Integer> createOrder(final Scanner scanner,final Map<String,Double> menu, final Logger log) {
        final Map<String, Integer> order = new ConcurrentHashMap<>();

        while (true) {
            log.fine("Enter the meal name: ");
            final String meal = scanner.nextLine().toLowerCase(Locale.US);

            if (YES.equals(meal)) {
                break;
            }

            if (menu.containsKey(meal)) {
                log.fine("Enter the quantity: ");
                final int quantity = Integer.parseInt(scanner.nextLine());

                if (quantity > 0) {
                    order.put(meal, quantity);
                } else {
                    log.fine("Quantity must be a positive integer greater than zero.");
                }
            } else {
                log.fine("Invalid meal selection. Please choose a valid meal from the menu.");
            }
        }
        return order;
    }
    
    
    private static double calculateTotalCost(final Map<String, Integer> order, final Map<String, Double> menu) {
        double totalCost = 0;

        for (final Map.Entry<String, Integer> entry : order.entrySet()) {
            final String meal = entry.getKey();
            final int quantity = entry.getValue();
            totalCost += menu.get(meal) * quantity;
        }

        if (order.size() > MAX_ORDER_SIZE) {
            totalCost *= HIGH_DISCOUNT;
        } else if (order.size() > MIN_ORDER_SIZE) {
            totalCost *= LOW_DISCOUNT;
        }

        if (totalCost > HIGH_TOTAL_COST) {
            totalCost -= 25;
        } else if (totalCost > LOW_TOTAL_COST) {
            totalCost -= 10;
        }

        return totalCost;
    }
    
    
    private static void displayOrderSummary(final Map<String, Integer> order, final double totalCost,final Logger log) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Selected meals and quantities:");
            for (final Map.Entry<String, Integer> entry : order.entrySet()) {
                log.fine(entry.getKey() + " x" + entry.getValue());
            }
            log.fine("Total cost: $" + totalCost);
        }
    }
    
    private static void confirmOrder(final Scanner scanner, final Logger log, final double totalCost) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Confirm your order (" + YES + "/no): ");
            final String confirmation = scanner.nextLine().toLowerCase(Locale.US);

            if (log.isLoggable(Level.FINE)) {
            	if (YES.equals(confirmation) && log.isLoggable(Level.FINE)) {
            	    log.fine("Order confirmed. Total cost: $" + (int) totalCost);
            	} else {
            	    log.fine("Order canceled.");
            	}
            }

        }
    }
    
    private static Map<String, Double> createMenu() {
        final Map<String, Double> menu = new ConcurrentHashMap<>();
        menu.put("burger", 10.0);
        menu.put("pizza", 15.0);
        menu.put("pasta", 12.0);
        return menu;
    }
}
