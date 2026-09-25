package studentrecords;

import common.Student;

/**
 * Manages student records using a custom singly linked list.
 *
 * Owned by: Member 1
 * Covers assignment requirements: 1 (store student records),
 * 2 (linked list storage), 12 (add/update/delete/search/display).
 *
 * INTEGRATION NOTE:
 * This class does NOT know about the stack, queue, BST, hash table, or
 * graph. It only manages the linked list. SystemManager.java is
 * responsible for calling into this class AND the BST/hash table/stack
 * together whenever a record is added, updated, or deleted, so that all
 * data structures stay in sync. Keeping this class self-contained is what
 * lets it be developed and tested independently.
 */
public class StudentLinkedList {

    private StudentNode head;
    private int size;

    public StudentLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds a new student record to the end of the list.
     *
     * @return true if added successfully, false if a student with the
     *         same ID already exists (duplicate IDs are rejected here,
     *         satisfying requirement 14).
     */
    public boolean addStudent(Student student) {
        if (student == null || student.getStudentId() == null) {
            return false;
        }
        if (searchById(student.getStudentId()) != null) {
            // Duplicate ID — reject
            return false;
        }

        StudentNode newNode = new StudentNode(student);
        if (head == null) {
            head = newNode;
        } else {
            StudentNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
        return true;
    }

    /**
     * Updates the name, programme, and marks of an existing student,
     * identified by student ID. The ID itself is not changed here.
     *
     * @return true if the student was found and updated, false if no
     *         matching student ID exists.
     */
    public boolean updateStudent(String studentId, String newName, String newProgramme, double newMarks) {
        StudentNode current = head;
        while (current != null) {
            if (current.getStudent().getStudentId().equals(studentId)) {
                current.getStudent().setName(newName);
                current.getStudent().setProgramme(newProgramme);
                current.getStudent().setMarks(newMarks);
                return true;
            }
            current = current.getNext();
        }
        return false; // Not found
    }

    /**
     * Removes a student record from the list by student ID.
     *
     * @return the removed Student object if found and deleted, or null
     *         if no student with that ID exists. Returning the removed
     *         Student allows SystemManager to log it to the action
     *         stack (requirement 3) and remove it from the BST/hash
     *         table as well.
     */
    public Student deleteStudent(String studentId) {
        if (head == null || studentId == null) {
            return null;
        }

        if (head.getStudent().getStudentId().equals(studentId)) {
            Student removed = head.getStudent();
            head = head.getNext();
            size--;
            return removed;
        }

        StudentNode current = head;
        while (current.getNext() != null) {
            if (current.getNext().getStudent().getStudentId().equals(studentId)) {
                Student removed = current.getNext().getStudent();
                current.setNext(current.getNext().getNext());
                size--;
                return removed;
            }
            current = current.getNext();
        }

        return null; // Not found
    }

    /**
     * Linear search through the list by student ID.
     * Note: for FAST lookups by ID, SystemManager should prefer the hash
     * table (Member 3's module) instead of this method. This linear
     * search exists to satisfy the linked list's own responsibilities
     * and is used internally for duplicate-checking and updates.
     *
     * @return the matching Student, or null if not found.
     */
    public Student searchById(String studentId) {
        if (studentId == null) {
            return null;
        }
        StudentNode current = head;
        while (current != null) {
            if (current.getStudent().getStudentId().equals(studentId)) {
                return current.getStudent();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Prints all student records currently stored in the linked list,
     * in insertion order. Satisfies requirement: "Display All Records
     * using Linked List" (menu item 4).
     */
    public void displayAll() {
        if (head == null) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("---- Student Records (Linked List) ----");
        StudentNode current = head;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.getStudent());
            current = current.getNext();
            count++;
        }
        System.out.println("Total records: " + size);
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
