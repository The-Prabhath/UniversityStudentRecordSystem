package app;

/**
 * Central integration point for the whole system. Holds one instance
 * of each member's module and coordinates operations that need to
 * touch more than one module (e.g. adding a student updates the
 * linked list, BST, and hash table together, then logs it to the
 * action stack).
 *
 * THIS IS A SKELETON, pushed to main on Day 1 so every member's
 * branch starts from the same base. Fields are commented out below —
 * as each member's Pull Request is merged into main, uncomment their
 * field, initialize it in the constructor, and add the orchestration
 * methods that use it.
 *
 * Do NOT let two people edit this file's TODO sections for different
 * modules on the same day without pulling/pushing in between — merge
 * PRs one at a time and update this file right after each merge.
 */
public class SystemManager {

    // ---- Member 1: Linked List ----
    // private studentrecords.StudentLinkedList linkedList;

    // ---- Member 2: Stack and Queue ----
    // private actionsqueue.ActionStack actionStack;
    // private actionsqueue.ServiceQueue serviceQueue;

    // ---- Member 3: BST and Hashing ----
    // private searchindex.StudentBST bst;
    // private searchindex.StudentHashTable hashTable;

    // ---- Member 4: Graph ----
    // private campusgraph.CampusGraph campusGraph;
    // private campusgraph.GraphTraversal graphTraversal;

    public SystemManager() {
        // Initialize each module's object here once its field above
        // is uncommented, e.g.:
        // linkedList = new studentrecords.StudentLinkedList();
    }

    // ------------------------------------------------------------
    // TODO (integration, after Member 1 + Member 3 are merged):
    //
    // public boolean addStudent(String id, String name, String programme, double marks) {
    //     if (hashTable.getById(id) != null) {
    //         return false; // duplicate ID
    //     }
    //     common.Student student = new common.Student(id, name, programme, marks);
    //     linkedList.addStudent(student);
    //     bst.insert(student);
    //     hashTable.put(student);
    //     actionStack.push(new actionsqueue.ActionRecord("ADD", id, "Student added"));
    //     return true;
    // }
    //
    // Follow the same pattern for updateStudent() and deleteStudent():
    // update/remove from linkedList + bst + hashTable, then log to
    // actionStack.
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // TODO (integration, after Member 2 is merged):
    //
    // public void addServiceRequest(String studentId, String requestType) {
    //     serviceQueue.enqueue(new actionsqueue.ServiceRequest(studentId, requestType));
    // }
    //
    // public void processNextServiceRequest() {
    //     actionsqueue.ServiceRequest next = serviceQueue.dequeue();
    //     if (next == null) {
    //         System.out.println("No pending service requests.");
    //     } else {
    //         System.out.println("Processing: " + next);
    //     }
    // }
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // TODO (integration, after Member 4 is merged):
    //
    // public void addCampusLocation(String location) {
    //     campusGraph.addLocation(location);
    // }
    //
    // public void traverseCampus(String startLocation, boolean useBFS) {
    //     graphTraversal.displayTraversal(campusGraph, startLocation, useBFS);
    // }
    // ------------------------------------------------------------
}
