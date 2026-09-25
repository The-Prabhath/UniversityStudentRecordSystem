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
