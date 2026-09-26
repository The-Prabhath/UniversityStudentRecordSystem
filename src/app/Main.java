package app;

import actionsqueue.ServiceRequest;
import common.InputValidator;
import common.Student;

import java.util.Scanner;

/**
 * Entry point for the University Student Record and Campus Route
 * Management System. Handles menu display and user input only — all
 * real logic lives in SystemManager and the individual module classes.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SystemManager manager = new SystemManager();

    public static void main(String[] args) {
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
                case 1 -> handleAddStudent();
                case 2 -> handleUpdateStudent();
                case 3 -> handleDeleteStudent();
                case 4 -> manager.displayAllStudentsLinkedList();
                case 5 -> handleAddServiceRequest();
                case 6 -> handleProcessServiceRequest();
                case 7 -> manager.displayRecentActions();
                case 8 -> manager.displayAllStudentsBST();
                case 9 -> handleSearchStudent();
                case 10 -> handleAddCampusLocation();
                case 11 -> handleRemoveCampusLocation();
                case 12 -> handleAddCampusConnection();
                case 13 -> handleRemoveCampusConnection();
                case 14 -> manager.displayCampusConnections();
                case 15 -> handleTraverseCampus();
                case 16 -> {
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 16.");
            }

            System.out.println();
        }

        scanner.close();
    }

    // ================= Menu Handlers =================

    private static void handleAddStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        if (!InputValidator.isValidId(id)) {
            System.out.println("Invalid Student ID. It cannot be empty.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        if (!InputValidator.isNonEmpty(name)) {
            System.out.println("Invalid name. It cannot be empty.");
            return;
        }

        System.out.print("Enter Programme: ");
        String programme = scanner.nextLine().trim();
        if (!InputValidator.isNonEmpty(programme)) {
            System.out.println("Invalid programme. It cannot be empty.");
            return;
        }

        System.out.print("Enter Marks (0-100): ");
        double marks = InputValidator.parseMarks(scanner.nextLine());
        if (!InputValidator.isValidMarks(marks)) {
            System.out.println("Invalid marks. Must be a number between 0 and 100.");
            return;
        }

        String result = manager.addStudent(id, name, programme, marks);
        System.out.println(result.equals("OK") ? "Student added successfully." : result);
    }

    private static void handleUpdateStudent() {
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter new Name: ");
        String name = scanner.nextLine().trim();
        if (!InputValidator.isNonEmpty(name)) {
            System.out.println("Invalid name. It cannot be empty.");
            return;
        }

        System.out.print("Enter new Programme: ");
        String programme = scanner.nextLine().trim();
        if (!InputValidator.isNonEmpty(programme)) {
            System.out.println("Invalid programme. It cannot be empty.");
            return;
        }

        System.out.print("Enter new Marks (0-100): ");
        double marks = InputValidator.parseMarks(scanner.nextLine());
        if (!InputValidator.isValidMarks(marks)) {
            System.out.println("Invalid marks. Must be a number between 0 and 100.");
            return;
        }

        String result = manager.updateStudent(id, name, programme, marks);
        System.out.println(result.equals("OK") ? "Student updated successfully." : result);
    }

    private static void handleDeleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();
        String result = manager.deleteStudent(id);
        System.out.println(result.equals("OK") ? "Student deleted successfully." : result);
    }

    private static void handleAddServiceRequest() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        if (!InputValidator.isValidId(id)) {
            System.out.println("Invalid Student ID.");
            return;
        }
        System.out.print("Enter Request Type (e.g. Transcript, ID Card): ");
        String type = scanner.nextLine().trim();
        if (!InputValidator.isNonEmpty(type)) {
            System.out.println("Invalid request type.");
            return;
        }
        manager.addServiceRequest(id, type);
        System.out.println("Service request added to queue.");
    }

    private static void handleProcessServiceRequest() {
        ServiceRequest processed = manager.processNextServiceRequest();
        if (processed == null) {
            System.out.println("No pending service requests.");
        } else {
            System.out.println("Processed: " + processed);
        }
    }

    private static void handleSearchStudent() {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine().trim();
        Student found = manager.searchStudentByHash(id);
        if (found == null) {
            System.out.println("No student found with ID \"" + id + "\".");
        } else {
            System.out.println("Found: " + found);
        }
    }

    private static void handleAddCampusLocation() {
        System.out.print("Enter location name: ");
        String location = scanner.nextLine().trim();
        if (!InputValidator.isNonEmpty(location)) {
            System.out.println("Invalid location name.");
            return;
        }
        boolean added = manager.addCampusLocation(location);
        System.out.println(added ? "Location added." : "That location already exists.");
    }

    private static void handleRemoveCampusLocation() {
        System.out.print("Enter location name to remove: ");
        String location = scanner.nextLine().trim();
        boolean removed = manager.removeCampusLocation(location);
        System.out.println(removed ? "Location removed." : "Location not found.");
    }

    private static void handleAddCampusConnection() {
        System.out.print("Enter first location: ");
        String a = scanner.nextLine().trim();
        System.out.print("Enter second location: ");
        String b = scanner.nextLine().trim();
        boolean added = manager.addCampusConnection(a, b);
        System.out.println(added ? "Connection added." :
                "Could not add connection — check both locations exist and aren't already connected.");
    }

    private static void handleRemoveCampusConnection() {
        System.out.print("Enter first location: ");
        String a = scanner.nextLine().trim();
        System.out.print("Enter second location: ");
        String b = scanner.nextLine().trim();
        boolean removed = manager.removeCampusConnection(a, b);
        System.out.println(removed ? "Connection removed." :
                "Could not remove connection — it may not exist.");
    }

    private static void handleTraverseCampus() {
        System.out.print("Enter starting location: ");
        String start = scanner.nextLine().trim();
        System.out.print("Traverse using (1) BFS or (2) DFS? ");
        String choice = scanner.nextLine().trim();
        boolean useBFS = !choice.equals("2");
        manager.traverseCampus(start, useBFS);
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
