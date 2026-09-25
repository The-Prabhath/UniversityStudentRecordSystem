package app;

import common.Student;

import java.util.Scanner;

/**
 * Entry point for the University Student Record and Campus Route
 * Management System.
 *
 * Main.java stays thin — it only reads menu input and validates it,
 * then delegates all real logic to SystemManager.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemManager manager = new SystemManager();
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 16.\n");
                continue;
            }

            switch (choice) {
                // ---- Member 1: Student Records (linked list) ----
                case 1:
                    addStudent(scanner, manager);
                    break;
                case 2:
                    updateStudent(scanner, manager);
                    break;
                case 3:
                    deleteStudent(scanner, manager);
                    break;
                case 4:
                    manager.displayAllStudents();
                    break;

                // ---- Member 2: Actions Queue (stack + service queue) ----
                case 5:
                    addServiceRequest(scanner, manager);
                    break;
                case 6:
                    processNextServiceRequest(manager);
                    break;
                case 7:
                    manager.displayRecentActions();
                    break;

                // ---- Member 3: Search Index (BST + hash table) ----
                case 8:
                    manager.displayStudentsByBST();
                    break;
                case 9:
                    searchStudentByHash(scanner, manager);
                    break;

                // ---- Member 4: Campus Graph (locations + BFS/DFS) ----
                case 10:
                    addCampusLocation(scanner, manager);
                    break;
                case 11:
                    removeCampusLocation(scanner, manager);
                    break;
                case 12:
                    addCampusConnection(scanner, manager);
                    break;
                case 13:
                    removeCampusConnection(scanner, manager);
                    break;
                case 14:
                    manager.displayCampusConnections();
                    break;
                case 15:
                    traverseCampus(scanner, manager);
                    break;

                case 16:
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 16.");
            }

            System.out.println();
        }

        scanner.close();
    }

    // ------------------------------------------------------------
    // Menu action helpers (Main stays thin; all real logic is in
    // SystemManager and each member's own module classes).
    // ------------------------------------------------------------

    // ---- Member 1: Student Records — add/update/delete prompts ----

    private static void addStudent(Scanner scanner, SystemManager manager) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Programme: ");
        String programme = scanner.nextLine().trim();
        double marks = readDouble(scanner, "Enter Marks: ");

        if (manager.addStudent(id, name, programme, marks)) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Could not add student: a student with ID \"" + id + "\" already exists.");
        }
    }

    private static void updateStudent(Scanner scanner, SystemManager manager) {
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter new Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter new Programme: ");
        String programme = scanner.nextLine().trim();
        double marks = readDouble(scanner, "Enter new Marks: ");

        if (manager.updateStudent(id, name, programme, marks)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Could not update: no student found with ID \"" + id + "\".");
        }
    }

    private static void deleteStudent(Scanner scanner, SystemManager manager) {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();

        if (manager.deleteStudent(id)) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Could not delete: no student found with ID \"" + id + "\".");
        }
    }

    // ---- Member 2: Actions Queue — service request prompts ----

    private static void addServiceRequest(Scanner scanner, SystemManager manager) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Request Type (e.g. Transcript Request): ");
        String requestType = scanner.nextLine().trim();

        manager.addServiceRequest(id, requestType);
        System.out.println("Service request added to the queue.");
    }

    private static void processNextServiceRequest(SystemManager manager) {
        var next = manager.processNextServiceRequest();
        if (next == null) {
            System.out.println("No pending service requests.");
        } else {
            System.out.println("Processing: " + next);
        }
    }

    // ---- Member 3: Search Index — hash lookup prompt ----

    private static void searchStudentByHash(Scanner scanner, SystemManager manager) {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine().trim();

        Student result = manager.searchStudentByHash(id);
        if (result == null) {
            System.out.println("No student found with ID \"" + id + "\".");
        } else {
            System.out.println("Found: " + result);
        }
    }

    // ---- Member 4: Campus Graph — location/connection/traversal prompts ----

    private static void addCampusLocation(Scanner scanner, SystemManager manager) {
        System.out.print("Enter new location name: ");
        String location = scanner.nextLine().trim();

        if (manager.addCampusLocation(location)) {
            System.out.println("Location added successfully.");
        } else {
            System.out.println("Could not add location: it already exists or the name is blank.");
        }
    }

    private static void removeCampusLocation(Scanner scanner, SystemManager manager) {
        System.out.print("Enter location name to remove: ");
        String location = scanner.nextLine().trim();

        if (manager.removeCampusLocation(location)) {
            System.out.println("Location removed successfully.");
        } else {
            System.out.println("Could not remove: location \"" + location + "\" was not found.");
        }
    }

    private static void addCampusConnection(Scanner scanner, SystemManager manager) {
        System.out.print("Enter first location: ");
        String a = scanner.nextLine().trim();
        System.out.print("Enter second location: ");
        String b = scanner.nextLine().trim();

        if (manager.addCampusConnection(a, b)) {
            System.out.println("Connection added successfully.");
        } else {
            System.out.println("Could not add connection: check that both locations exist and aren't already connected.");
        }
    }

    private static void removeCampusConnection(Scanner scanner, SystemManager manager) {
        System.out.print("Enter first location: ");
        String a = scanner.nextLine().trim();
        System.out.print("Enter second location: ");
        String b = scanner.nextLine().trim();

        if (manager.removeCampusConnection(a, b)) {
            System.out.println("Connection removed successfully.");
        } else {
            System.out.println("Could not remove connection: it does not exist.");
        }
    }

    private static void traverseCampus(Scanner scanner, SystemManager manager) {
        System.out.print("Enter starting location: ");
        String start = scanner.nextLine().trim();
        System.out.print("Use BFS instead of DFS? (y/n): ");
        boolean useBFS = scanner.nextLine().trim().equalsIgnoreCase("y");

        manager.traverseCampus(start, useBFS);
    }

    /** Reads a double from the scanner, re-prompting on invalid input. */
    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("===== University Student Record and Campus Route Management System =====");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST/AVL");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
    }
}
