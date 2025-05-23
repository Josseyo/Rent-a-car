import java.util.Scanner;

/**
 * Main class for LTU Rent-a-car system.
 * Handles car fleet management and rental operations.
 *
 *
 * @author annyos-4 Josefin Yoshida Dahlqvist
 */

public class Main {
    /** Maximum number of cars in the fleet. */
    private static final int MAX_CARS = 100;
    /** Maximum number of rentals allowed. */
    private static final int MAX_RENTALS = 100;
    /** Hourly rental rate in SEK. */
    private static final double HOURLY_RATE = 120.0;
    /** Factor for rounding costs. */
    private static final double ROUNDING_FACTOR = 100.0;
    private static final double MINUTES_PER_HOUR = 60.0;
    private static final int MENU_ADD_CAR = 1;
    private static final int MENU_RENT_CAR = 2;
    private static final int MENU_RETURN_CAR = 3;
    private static final int MENU_PRINT_FLEET = 4;
    private static final int MENU_PRINT_RENTAL_SUMMARY = 5;

    /** Scanner for user input. */
    private static Scanner userInput = new Scanner(System.in);

    /**
     * Entry point for the application.
     * @param args Command-line arguments (not used)
     */
    public static void main(final String[] args) {
        final String[] regNumbers = new String[MAX_CARS];
        final String[] makeModels = new String[MAX_CARS];
        final boolean[] rented = new boolean[MAX_CARS];
        int carCount = 0;

        final String[] rentalRegNumbers = new String[MAX_RENTALS];
        final String[] rentalRenterNames = new String[MAX_RENTALS];
        final String[] rentalPickupTimes = new String[MAX_RENTALS];
        final String[] rentalReturnTimes = new String[MAX_RENTALS];
        final double[] rentalCosts = new double[MAX_RENTALS];
        int rentalCount = 0;

    // ========== TEST DATA ==========
        
        regNumbers[0] = "ABC123";
        makeModels[0] = "Volvo XC90";
        rented[0] = false;
        
        regNumbers[1] = "XYZ789"; 
        makeModels[1] = "Toyota Prius";
        rented[1] = true;  // Currently rented
        
        regNumbers[2] = "DEF456";
        makeModels[2] = "Tesla Model S";
        rented[2] = false;
        carCount = 3;

        // Add 2 test rentals
        rentalRegNumbers[0] = "XYZ789";
        rentalRenterNames[0] = "John Doe";
        rentalPickupTimes[0] = "09:00";  // Active rental (no return time)
        
        rentalRegNumbers[1] = "DEF456";
        rentalRenterNames[1] = "Jane Smith";
        rentalPickupTimes[1] = "10:00";
        rentalReturnTimes[1] = "15:30";  // Completed rental
        rentalCosts[1] = 660.0;         // 5.5 hours * 120 SEK
        rentalCount = 2;
        // ========== END TEST DATA ==========

        // Main menu loop
        while (true) {
            printMenu();
            final String input = userInput.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("End program");
                break;
            }

            final int option = parseMenuOption(input);
            if (option == -1) {
                System.out.println("invalid menu item");
                continue;
            }

            switch (option) {
                case MENU_ADD_CAR:
                    carCount = addCarToFleet(regNumbers, makeModels, rented, carCount);
                    break;
                case MENU_RENT_CAR:

                    rentalCount = rentCar(
                        regNumbers, makeModels, rented, carCount,
                        rentalRegNumbers, rentalRenterNames, rentalPickupTimes, rentalCount
                    );
                    break;
                case MENU_RETURN_CAR:
                    rentalCount = returnCar(
                        regNumbers, makeModels, rented, carCount,
                        rentalRegNumbers, rentalRenterNames, rentalPickupTimes,
                        rentalReturnTimes, rentalCosts, rentalCount
                    );
                    break;
                case MENU_PRINT_FLEET:
                    printCarFleet(regNumbers, makeModels, rented, carCount);
                    break;
                case MENU_PRINT_RENTAL_SUMMARY:
                    printRentalSummary(
                        rentalRegNumbers, rentalRenterNames, rentalPickupTimes,
                        rentalReturnTimes, rentalCosts, rentalCount
                    );
                    break;
                default:
                    System.out.println("invalid menu item");
            }
        }
    }

    /**
     * Parses the menu option from user input.
     * @param input User input string
     * @return Parsed integer option, or -1 if invalid
     */
    private static int parseMenuOption(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Prints the main menu.
     */
    private static void printMenu() {
        System.out.println("----------------------------------");
        System.out.println("# LTU Rent-a-car");
        System.out.println("----------------------------------");
        System.out.println("1. Add car to fleet");
        System.out.println("2. Rent a car");
        System.out.println("3. Return a car");
        System.out.println("4. Print car fleet");
        System.out.println("5. Print rental summary");
        System.out.println("q. End program");
        System.out.print("> Enter your option: ");
    }

    /**
     * Adds a new car to the fleet.
     * @param regNumbers Array of registration numbers
     * @param makeModels Array of make and model strings
     * @param rented Array of rental status
     * @param carCount Current number of cars
     * @return Updated car count
     */
    private static int addCarToFleet(
        final String[] regNumbers, final String[] makeModels, final boolean[] rented, final int carCount) {
        if (carCount >= MAX_CARS) {
            System.out.println("Car fleet is full!");
            return carCount;
        }
        System.out.print("> Enter registration number: ");
        final String reg = userInput.nextLine().trim();
        if (!reg.matches("^[A-Z]{3}[0-9]{3}$")) {
            System.out.println("invalid registration number");
            return carCount;
        }
        if (isDuplicateRegNumber(regNumbers, carCount, reg)) {
            System.out.println("number " + reg + " already exists");
            return carCount;
        } 
        System.out.print("> Enter make and model: ");
        final String mm = userInput.nextLine();
        regNumbers[carCount] = reg;
        makeModels[carCount] = mm;
        rented[carCount] = false;
        System.out.println(
            mm + " with registration number " + reg
            + " was added to car fleet."
        );
        return carCount + 1;
    }

    /**
     * Checks if a registration number already exists.
     * @param regNumbers Array of registration numbers
     * @param carCount Number of cars
     * @param reg Registration number to check
     * @return True if duplicate, false otherwise
     */
    private static boolean isDuplicateRegNumber(
        final String[] regNumbers, final int carCount, final String reg) {
        for (int i = 0; i < carCount; i++) {
            if (regNumbers[i].equals(reg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles renting a car.
     * @param regNumbers Array of registration numbers
     * @param makeModels Array of make and model strings
     * @param rented Array of rental status
     * @param carCount Number of cars
     * @param rentalRegNumbers Array of rental registration numbers
     * @param rentalRenterNames Array of renter names
     * @param rentalPickupTimes Array of pickup times
     * @param rentalCount Number of rentals
     * @return Updated rental count
     */
    private static int rentCar(
        final String[] regNumbers, final String[] makeModels, final boolean[] rented, final int carCount,
        final String[] rentalRegNumbers, final String[] rentalRenterNames, final String[] rentalPickupTimes, final int rentalCount) {
        System.out.print("> Enter car's registration number: ");
        final String reg = userInput.nextLine().trim();
        final int carIndex = findCarIndex(regNumbers, carCount, reg);
        if (carIndex == -1) {
            System.out.println("car " + reg + " not found");
            return rentalCount;
        }
        if (rented[carIndex]) {
            System.out.println("car " + reg + " not available");
            return rentalCount;
        }
        System.out.print("> Enter time of pickup: ");
        final String pickupTime = userInput.nextLine().trim();
        if (!isValidTime(pickupTime)) {
            System.out.println("invalid time format");
            return rentalCount;
        }
        System.out.print("> Enter renter's name: ");
        final String renterName = userInput.nextLine();
        rented[carIndex] = true;
        rentalRegNumbers[rentalCount] = reg;
        rentalRenterNames[rentalCount] = renterName;
        rentalPickupTimes[rentalCount] = pickupTime;
        System.out.println(
            "Car with registration number " + reg
            + " was rented by " + renterName
            + " at " + pickupTime + "."
        );
        return rentalCount + 1;
    }

    /**
     * Finds the index of a car by registration number.
     * @param regNumbers Array of registration numbers
     * @param carCount Number of cars
     * @param reg Registration number to find
     * @return Index if found, -1 otherwise
     */
    private static int findCarIndex(final String[] regNumbers, final int carCount, final String reg) {
        for (int i = 0; i < carCount; i++) {
            if (regNumbers[i].equals(reg)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Handles returning a car.
     * @param regNumbers Array of registration numbers
     * @param makeModels Array of make and model strings
     * @param rented Array of rental status
     * @param carCount Number of cars
     * @param rentalRegNumbers Array of rental registration numbers
     * @param rentalRenterNames Array of renter names
     * @param rentalPickupTimes Array of pickup times
     * @param rentalReturnTimes Array of return times
     * @param rentalCosts Array of rental costs
     * @param rentalCount Number of rentals
     * @return Updated rental count
     */
    private static int returnCar(
        final String[] regNumbers, final String[] makeModels, final boolean[] rented, final int carCount,
        final String[] rentalRegNumbers, final String[] rentalRenterNames, final String[] rentalPickupTimes,
        final String[] rentalReturnTimes, final double[] rentalCosts, final int rentalCount) {
        System.out.print("> Enter registration number: ");
        final String reg = userInput.nextLine().trim();
        final int carIndex = findCarIndex(regNumbers, carCount, reg);
        if (carIndex == -1) {
            System.out.println("car " + reg + " not found");
            return rentalCount;
        }
        if (!rented[carIndex]) {
            System.out.println("car " + reg + " not rented");
            return rentalCount;
        }
        final int rentalIndex = findActiveRentalIndex(rentalRegNumbers, rentalReturnTimes, rentalCount, reg);
        if (rentalIndex == -1) {
            System.out.println("car " + reg + " not rented");
            return rentalCount;
        }
        System.out.print("> Enter time of return: ");
        final String returnTime = userInput.nextLine().trim();
        if (!isValidTime(returnTime)) {
            System.out.println("invalid time format");
            return rentalCount;
        }
        final String pickupTime = rentalPickupTimes[rentalIndex];
        if (!isTimeBefore(pickupTime, returnTime)) {
            System.out.println("invalid time format");
            return rentalCount;
        }
        final double hours = timeDiffInHours(pickupTime, returnTime);
        final double cost = Math.round(hours * HOURLY_RATE * ROUNDING_FACTOR)
            / ROUNDING_FACTOR;
        rentalReturnTimes[rentalIndex] = returnTime;
        rentalCosts[rentalIndex] = cost;
        rented[carIndex] = false;
        printReturnReceipt(
            rentalRenterNames[rentalIndex], makeModels[carIndex], reg,
            pickupTime, returnTime, hours, cost
        );
        return rentalCount;
    }

    /**
     * Finds the index of an active rental for a specific registration number.
     * @param rentalRegNumbers Array of rental registration numbers
     * @param rentalReturnTimes Array of return times
     * @param rentalCount Number of rentals
     * @param reg Registration number to find
     * @return Index if found, -1 otherwise
     */
    private static int findActiveRentalIndex(
        final String[] rentalRegNumbers, final String[] rentalReturnTimes, final int rentalCount, final String reg) {
        for (int i = 0; i < rentalCount; i++) {
            if (rentalRegNumbers[i].equals(reg)
                && (rentalReturnTimes[i] == null || rentalReturnTimes[i].isEmpty())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Prints a return receipt for a rental.
     * @param renterName Name of the renter
     * @param makeModel Car make and model
     * @param reg Registration number
     * @param pickupTime Pickup time
     * @param returnTime Return time
     * @param hours Rental duration in hours
     * @param cost Rental cost
     */
    private static void printReturnReceipt(
        final String renterName, final String makeModel, final String reg,
        final String pickupTime, final String returnTime, final double hours, final double cost) {
        System.out.println("===================================");
        System.out.println("LTU Rent-a-car");
        System.out.println("===================================");
        System.out.println("Name: " + renterName);
        System.out.println("Car: " + makeModel + " (" + reg + ")");
        System.out.println("Time: " + pickupTime + "-" + returnTime
            + " (" + hours + " hours)");
        System.out.println("Total cost: " + (int) cost + " SEK");
    }

    /**
     * Prints the car fleet.
     * @param regNumbers Array of registration numbers
     * @param makeModels Array of make and model strings
     * @param rented Array of rental status
     * @param carCount Number of cars
     */
    private static void printCarFleet(
        final String[] regNumbers, final String[] makeModels, final boolean[] rented, final int carCount) {
        System.out.println("LTU Rent-a-car car fleet:");
        System.out.println("Fleet:");
        System.out.println("Model\t\tNumberplate\tStatus");
        int available = 0;
        for (int i = 0; i < carCount; i++) {
            System.out.println(
                makeModels[i] + "\t" + regNumbers[i] + "\t"
                + (rented[i] ? "Rented" : "Available")
            );
            if (!rented[i]) {
                available++;
            }
        }
        System.out.println("Total number of cars: " + carCount);
        System.out.println("Total number of available cars: " + available);
    }

    /**
     * Prints a summary of all rentals.
     * @param rentalRegNumbers Array of rental registration numbers
     * @param rentalRenterNames Array of renter names
     * @param rentalPickupTimes Array of pickup times
     * @param rentalReturnTimes Array of return times
     * @param rentalCosts Array of rental costs
     * @param rentalCount Number of rentals
     */
    private static void printRentalSummary(
        final String[] rentalRegNumbers, final String[] rentalRenterNames,
        final String[] rentalPickupTimes, final String[] rentalReturnTimes,
        final double[] rentalCosts, final int rentalCount) {
        System.out.println("LTU Rent-a-car rental summary:");
        System.out.println("Rentals:");
        System.out.println("Name\t\tNumberplate\tPickup\tReturn\tCost");
        double totalRevenue = 0;
        for (int i = 0; i < rentalCount; i++) {
            final String returnTime = rentalReturnTimes[i] == null ? "" : rentalReturnTimes[i];
            final String cost = rentalReturnTimes[i] == null ? "" : (int) rentalCosts[i] + " SEK";
            System.out.println(
                rentalRenterNames[i] + "\t" + rentalRegNumbers[i] + "\t"
                + rentalPickupTimes[i] + "\t" + returnTime + "\t" + cost
            );
            if (!returnTime.isEmpty()) {
                totalRevenue += rentalCosts[i];
            }
        }
        System.out.println("Total number of rentals: " + rentalCount);
        System.out.println("Total revenue: " + (int) totalRevenue + " SEK");
    }

    /**
     * Validates time format (HH:mm).
     * @param time Time string
     * @return True if valid, false otherwise
     * Regex breakdown:
     * ^                Start of string
     *  (                Grouping start
     *  [0-1][0-9]     00-19 hours
     *  |              OR
     *  2[0-3]         20-23 hours
     *   )                Grouping end
     *   :                Literal colon
     *   [0-5][0-9]       00-59 minutes
     *   $                End of string
     *
     */
    private static boolean isValidTime(final String time) {
        return time.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$");
    }

    /**
     * Checks if t1 is before t2.
     * @param t1 Start time
     * @param t2 End time
     * @return True if t1 < t2, false otherwise
     */
    private static boolean isTimeBefore(final String t1, final String t2) {
        final int h1 = Integer.parseInt(t1.substring(0, 2));
        final int m1 = Integer.parseInt(t1.substring(3));
        final int h2 = Integer.parseInt(t2.substring(0, 2));
        final int m2 = Integer.parseInt(t2.substring(3));
        if (h1 < h2) {
            return true;
        }
        if (h1 == h2 && m1 < m2) {
            return true;
        }
        return false;
    }

    /**
     * Computes the time difference in hours between two times.
     * @param t1 Start time (HH:mm)
     * @param t2 End time (HH:mm)
     * @return Difference in hours as double
     */
    private static double timeDiffInHours(final String t1, final String t2) {
        final int h1 = Integer.parseInt(t1.substring(0, 2));
        final int m1 = Integer.parseInt(t1.substring(3));
        final int h2 = Integer.parseInt(t2.substring(0, 2));
        final int m2 = Integer.parseInt(t2.substring(3));
        return (h2 + m2 / MINUTES_PER_HOUR) - (h1 + m1 / MINUTES_PER_HOUR);
    }
}