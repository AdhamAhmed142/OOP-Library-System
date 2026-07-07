import java.util.List;
import java.util.Scanner;

/**
 * Menu-driven entry point for the Library Lending System.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    addItemMenu();
                    break;
                case 2:
                    addMemberMenu();
                    break;
                case 3:
                    borrowItemMenu();
                    break;
                case 4:
                    returnItemMenu();
                    break;
                case 5:
                    library.listCatalog();
                    break;
                case 6:
                    library.printReport();
                    break;
                case 7:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                case 8:
                    searchByTitleMenu();
                    break;
                case 9:
                    showAvailableMenu();
                    break;
                default:
                    System.out.println("Invalid choice, please pick an option from the menu.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Library Lending System =====");
        System.out.println("1. Add Item");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Item");
        System.out.println("4. Return Item");
        System.out.println("5. List Catalog");
        System.out.println("6. Report");
        System.out.println("7. Exit");
        System.out.println("8. Search Item by Title (bonus)");
        System.out.println("9. Show Available Items (bonus)");
    }

    // ---- menu actions ----

    private static void addItemMenu() {
        System.out.println("What kind of item? 1) Book  2) Magazine  3) DVD");
        int kind = readInt("Enter choice: ");

        String title = readLine("Title: ");

        try {
            switch (kind) {
                case 1: {
                    String author = readLine("Author: ");
                    int pages = readInt("Pages: ");
                    library.addItem(new Book(title, author, pages));
                    System.out.println("Book added.");
                    break;
                }
                case 2: {
                    int issueNumber = readInt("Issue number: ");
                    library.addItem(new Magazine(title, issueNumber));
                    System.out.println("Magazine added.");
                    break;
                }
                case 3: {
                    int runtime = readInt("Runtime (minutes): ");
                    library.addItem(new DVD(title, runtime));
                    System.out.println("DVD added.");
                    break;
                }
                default:
                    System.out.println("Unknown item kind.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add item: " + e.getMessage());
        }
    }

    private static void addMemberMenu() {
        try {
            String memberId = readLine("Member id: ");
            String name = readLine("Name: ");
            int maxAllowed = readInt("Max items allowed: ");
            library.addMember(new Member(memberId, name, maxAllowed));
            System.out.println("Member added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add member: " + e.getMessage());
        }
    }

    private static void borrowItemMenu() {
        String memberId = readLine("Member id: ");
        String itemId = readLine("Item id: ");
        try {
            library.borrowItem(memberId, itemId);
            System.out.println("Borrowed " + itemId + " to " + memberId + ".");
        } catch (LibraryException e) {
            System.out.println("Could not borrow: " + e.getMessage());
        }
    }

    private static void returnItemMenu() {
        String memberId = readLine("Member id: ");
        String itemId = readLine("Item id: ");
        try {
            library.returnItem(memberId, itemId);
            System.out.println("Returned " + itemId + " from " + memberId + ".");
        } catch (LibraryException e) {
            System.out.println("Could not return: " + e.getMessage());
        }
    }

    private static void searchByTitleMenu() {
        String text = readLine("Search text: ");
        List<LibraryItem> results = library.searchByTitle(text);
        if (results.isEmpty()) {
            System.out.println("No items matched.");
        } else {
            for (LibraryItem item : results) {
                item.displayInfo();
            }
        }
    }

    private static void showAvailableMenu() {
        List<LibraryItem> results = library.listAvailableItems();
        if (results.isEmpty()) {
            System.out.println("No items are currently available.");
        } else {
            for (LibraryItem item : results) {
                item.displayInfo();
            }
        }
    }

    // ---- input helpers (guard the menu against bad input) ----

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}
