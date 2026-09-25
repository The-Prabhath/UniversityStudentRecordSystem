package app;

import actionsqueue.ActionRecord;
import actionsqueue.ActionStack;
import actionsqueue.ServiceQueue;
import actionsqueue.ServiceRequest;
import campusgraph.CampusGraph;
import campusgraph.GraphTraversal;
import common.Student;
import searchindex.StudentBST;
import searchindex.StudentHashTable;
import studentrecords.StudentLinkedList;

/**
 * Central integration point for the whole system. Holds one instance
 * of each member's module and coordinates operations that need to
 * touch more than one module (e.g. adding a student updates the
 * linked list, BST, and hash table together, then logs it to the
 * action stack).
 *
 * SYNC NOTE: addStudent() inserts the exact same Student object into
 * the linked list, the BST, and the hash table. Because all three
 * structures hold a reference to that one object, updateStudent()
 * only needs to mutate it once (via the linked list) — the change is
 * automatically visible through the BST and hash table too, since a
 * student's ID (the BST/hash key) never changes on update. Deleting
 * a student still has to be done in all three structures explicitly,
 * since removing a node from one structure does not remove it from
 * the others.
 */
public class SystemManager {

    // ---- Member 1: Linked List ----
    private final StudentLinkedList linkedList;

    // ---- Member 2: Stack and Queue ----
    private final ActionStack actionStack;
    private final ServiceQueue serviceQueue;

    // ---- Member 3: BST and Hashing ----
    private final StudentBST bst;
    private final StudentHashTable hashTable;

    // ---- Member 4: Graph ----
    private final CampusGraph campusGraph;
    private final GraphTraversal graphTraversal;

    public SystemManager() {
        linkedList = new StudentLinkedList();
        actionStack = new ActionStack();
        serviceQueue = new ServiceQueue();
        bst = new StudentBST();
        hashTable = new StudentHashTable();
        campusGraph = new CampusGraph();
        graphTraversal = new GraphTraversal();
    }

    // ------------------------------------------------------------
    // Student record operations
    // Member 1 (linked list) does the add/update/delete/display work;
    // Member 3 (BST + hash table) is kept in sync alongside it.
    // ------------------------------------------------------------

    /**
     * Adds a student to the linked list, BST, and hash table together,
     * then logs the action. The hash table is checked first since it
     * gives the fastest duplicate-ID check.
     *
     * @return true if added, false if the Student ID already exists.
     */
    public boolean addStudent(String id, String name, String programme, double marks) {
        if (hashTable.getById(id) != null) {
            return false; // duplicate ID
        }
        Student student = new Student(id, name, programme, marks);
        linkedList.addStudent(student);
        bst.insert(student);
        hashTable.put(student);
        actionStack.push(new ActionRecord("ADD", id, "Student added"));
        return true;
    }

    /**
     * Updates a student's name, programme, and marks. Only the linked
     * list needs to be told directly — see the class-level SYNC NOTE
     * for why the BST and hash table pick up the change automatically.
     *
     * @return true if the student was found and updated, false otherwise.
     */
    public boolean updateStudent(String id, String newName, String newProgramme, double newMarks) {
        if (hashTable.getById(id) == null) {
            return false; // not found
        }
        boolean updated = linkedList.updateStudent(id, newName, newProgramme, newMarks);
        if (updated) {
            actionStack.push(new ActionRecord("UPDATE", id, "Student record updated"));
        }
        return updated;
    }

    /**
     * Removes a student from the linked list, BST, and hash table
     * together, then logs the action.
     *
     * @return true if the student existed and was removed, false otherwise.
     */
    public boolean deleteStudent(String id) {
        Student removed = linkedList.deleteStudent(id);
        if (removed == null) {
            return false; // not found
        }
        bst.delete(id);
        hashTable.remove(id);
        actionStack.push(new ActionRecord("DELETE", id, "Student removed"));
        return true;
    }

    /** Menu item 4 (Member 1): display all records in insertion order (linked list). */
    public void displayAllStudents() {
        linkedList.displayAll();
    }

    /** Menu item 8 (Member 3): display all records in ID order (BST in-order traversal). */
    public void displayStudentsByBST() {
        bst.displayAll();
    }

    /** Menu item 9 (Member 3): fast lookup by ID via the hash table. */
    public Student searchStudentByHash(String id) {
        return hashTable.getById(id);
    }

    // ------------------------------------------------------------
    // Service request / action history operations
    // Member 2: ServiceQueue (FIFO requests) + ActionStack (LIFO history)
    // ------------------------------------------------------------

    /** Menu item 5 (Member 2): add a service request to the back of the queue. */
    public void addServiceRequest(String studentId, String requestType) {
        serviceQueue.enqueue(new ServiceRequest(studentId, requestType));
    }

    /**
     * Menu item 6 (Member 2): process (dequeue) the next pending service
     * request and log it to the action history.
     *
     * @return the processed request, or null if the queue was empty.
     */
    public ServiceRequest processNextServiceRequest() {
        ServiceRequest next = serviceQueue.dequeue();
        if (next != null) {
            actionStack.push(new ActionRecord("SERVICE", next.getStudentId(),
                    "Processed request: " + next.getRequestType()));
        }
        return next;
    }

    /** (Member 2) Displays all pending service requests, in arrival order. */
    public void displayServiceQueue() {
        serviceQueue.displayQueue();
    }

    /** Menu item 7 (Member 2): display the recent-actions history, most recent first. */
    public void displayRecentActions() {
        actionStack.displayRecentActions();
    }

    // ------------------------------------------------------------
    // Campus graph operations
    // Member 4: CampusGraph (locations/roads as an adjacency list)
    // + GraphTraversal (BFS/DFS over that graph)
    // ------------------------------------------------------------

    /** Menu item 10 (Member 4): add a campus location (graph vertex). */
    public boolean addCampusLocation(String location) {
        return campusGraph.addLocation(location);
    }

    /** Menu item 11 (Member 4): remove a campus location and its connections. */
    public boolean removeCampusLocation(String location) {
        return campusGraph.removeLocation(location);
    }

    /** Menu item 12 (Member 4): add a two-way connection/road between two locations. */
    public boolean addCampusConnection(String locationA, String locationB) {
        return campusGraph.addConnection(locationA, locationB);
    }

    /** Menu item 13 (Member 4): remove the connection/road between two locations. */
    public boolean removeCampusConnection(String locationA, String locationB) {
        return campusGraph.removeConnection(locationA, locationB);
    }

    /** Menu item 14 (Member 4): display the full campus adjacency list. */
    public void displayCampusConnections() {
        campusGraph.displayConnections();
    }

    /** Menu item 15 (Member 4): traverse the campus network using BFS or DFS. */
    public void traverseCampus(String startLocation, boolean useBFS) {
        graphTraversal.displayTraversal(campusGraph, startLocation, useBFS);
    }
}
