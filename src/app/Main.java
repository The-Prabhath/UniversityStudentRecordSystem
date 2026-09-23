package app;

import java.util.Scanner;

/**
 * Entry point for the University Student Record and Campus Route
 * Management System.
 *
 * THIS IS A SKELETON. It only prints the menu and reads the user's
 * choice — it does not yet call into any module. As each member's
 * module is merged into main and wired into SystemManager, the TODO
 * blocks below get replaced with real calls, e.g.:
 *
 *     case 1:
 *         System.out.print("Enter Student ID: ");
 *         String id = scanner.nextLine();
 *         ... collect other fields ...
 *         manager.addStudent(id, name, programme, marks);
 *         break;
 *
 * Main.java should stay thin — all real logic belongs in
 * SystemManager.java or inside each member's own module classes.
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
                case 1:
                    // TODO (Member 1 + integration): Add Student Record
                    System.out.println("[Not yet implemented] Add Student Record");
                    break;
                case 2:
                    // TODO (Member 1 + integration): Update Student Record
                    System.out.println("[Not yet implemented] Update Student Record");
                    break;
                case 3:
                    // TODO (Member 1 + integration): Delete Student Record
                    System.out.println("[Not yet implemented] Delete Student Record");
                    break;
                case 4:
                    // TODO (Member 1): Display All Records using Linked List
                    System.out.println("[Not yet implemented] Display All Records (Linked List)");
                    break;
                case 5:
                    // TODO (Member 2): Add Service Request to Queue
                    System.out.println("[Not yet implemented] Add Service Request");
                    break;
                case 6:
                    // TODO (Member 2): Process Next Service Request
                    System.out.println("[Not yet implemented] Process Next Service Request");
                    break;
                case 7:
                    // TODO (Member 2): Display Recent Actions using Stack
                    System.out.println("[Not yet implemented] Display Recent Actions");
                    break;
                case 8:
                    // TODO (Member 3): Display Students using BST/AVL
                    System.out.println("[Not yet implemented] Display Students (BST)");
                    break;
                case 9:
                    // TODO (Member 3): Search Student using Hashing
                    System.out.println("[Not yet implemented] Search Student (Hashing)");
                    break;
                case 10:
                    // TODO (Member 4): Add Campus Location
                    System.out.println("[Not yet implemented] Add Campus Location");
                    break;
                case 11:
                    // TODO (Member 4): Remove Campus Location
                    System.out.println("[Not yet implemented] Remove Campus Location");
                    break;
                case 12:
                    // TODO (Member 4): Add Campus Connection/Road
                    System.out.println("[Not yet implemented] Add Campus Connection/Road");
                    break;
                case 13:
                    // TODO (Member 4): Remove Campus Connection/Road
                    System.out.println("[Not yet implemented] Remove Campus Connection/Road");
                    break;
                case 14:
                    // TODO (Member 4): Display Campus Connections
                    System.out.println("[Not yet implemented] Display Campus Connections");
                    break;
                case 15:
                    // TODO (Member 4): Traverse Campus Locations using BFS or DFS
                    System.out.println("[Not yet implemented] Traverse Campus Locations");
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
