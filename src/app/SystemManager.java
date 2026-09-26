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
 * touch more than one module.
 *
 * Every write to student records (add/update/delete) keeps the linked
 * list, BST, and hash table in sync, and logs the action to the stack.
 * The graph side is independent and simply delegates straight through.
 */
public class SystemManager {

    // Member 1
    private final StudentLinkedList linkedList;

    // Member 2
    private final ActionStack actionStack;
    private final ServiceQueue serviceQueue;

    // Member 3
    private final StudentBST bst;
    private final StudentHashTable hashTable;

    // Member 4
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

    // ================= Student Record Operations =================

    /**
     * Adds a student, keeping linked list, BST, and hash table in sync.
     *
     * @return "OK" on success, or an error message describing why it failed.
     */
    public String addStudent(String id, String name, String programme, double marks) {
        if (hashTable.getById(id) != null) {
            return "A student with ID \"" + id + "\" already exists.";
        }

        Student student = new Student(id, name, programme, marks);

        boolean addedToList = linkedList.addStudent(student);
        boolean addedToBst = bst.insert(student);
        boolean addedToHash = hashTable.put(student);

        if (!addedToList || !addedToBst || !addedToHash) {
            // Shouldn't happen given the duplicate check above, but keep
            // the three structures consistent if it ever does.
            return "Failed to add student — please try again.";
        }

        actionStack.push(new ActionRecord("ADD", id, "Added " + name));
        return "OK";
    }

    /**
     * Updates an existing student's name, programme, and marks across
     * the linked list, BST, and hash table.
     *
     * @return "OK" on success, or an error message.
     */
    public String updateStudent(String id, String newName, String newProgramme, double newMarks) {
        Student existing = hashTable.getById(id);
        if (existing == null) {
            return "No student found with ID \"" + id + "\".";
        }

        linkedList.updateStudent(id, newName, newProgramme, newMarks);

        // BST and hash table hold direct references into the same
        // Student objects as the linked list, so updating the fields on
        // the existing object (done inside updateStudent above) is
        // reflected everywhere automatically — no separate BST/hash
        // update call is needed here since they share the same object.

        actionStack.push(new ActionRecord("UPDATE", id, "Updated " + newName));
        return "OK";
    }

    /**
     * Deletes a student from the linked list, BST, and hash table
     * together, and logs the deletion.
     *
     * @return "OK" on success, or an error message.
     */
    public String deleteStudent(String id) {
        Student removed = linkedList.deleteStudent(id);
        if (removed == null) {
            return "No student found with ID \"" + id + "\".";
        }

        bst.delete(id);
        hashTable.remove(id);

        actionStack.push(new ActionRecord("DELETE", id, "Deleted " + removed.getName()));
        return "OK";
    }

    public void displayAllStudentsLinkedList() {
        linkedList.displayAll();
    }

    public void displayAllStudentsBST() {
        bst.displayAll();
    }

    public Student searchStudentByHash(String id) {
        return hashTable.getById(id);
    }

    // ================= Stack / Queue Operations =================

    public void addServiceRequest(String studentId, String requestType) {
        serviceQueue.enqueue(new ServiceRequest(studentId, requestType));
    }

    /**
     * @return the processed request, or null if the queue was empty.
     */
    public ServiceRequest processNextServiceRequest() {
        ServiceRequest next = serviceQueue.dequeue();
        if (next != null) {
            actionStack.push(new ActionRecord("PROCESS_REQUEST", next.getStudentId(),
                    "Processed request: " + next.getRequestType()));
        }
        return next;
    }

    public void displayServiceQueue() {
        serviceQueue.displayQueue();
    }

    public void displayRecentActions() {
        actionStack.displayRecentActions();
    }

    // ================= Graph Operations =================

    public boolean addCampusLocation(String location) {
        return campusGraph.addLocation(location);
    }

    public boolean removeCampusLocation(String location) {
        return campusGraph.removeLocation(location);
    }

    public boolean addCampusConnection(String locationA, String locationB) {
        return campusGraph.addConnection(locationA, locationB);
    }

    public boolean removeCampusConnection(String locationA, String locationB) {
        return campusGraph.removeConnection(locationA, locationB);
    }

    public void displayCampusConnections() {
        campusGraph.displayConnections();
    }

    public void traverseCampus(String startLocation, boolean useBFS) {
        graphTraversal.displayTraversal(campusGraph, startLocation, useBFS);
    }
}
