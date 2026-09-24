package searchindex;

import common.Student;

/**
 * Binary Search Tree that organizes student records by Student ID,
 * enabling ordered display and O(log n) average-case search on a
 * balanced tree.

 * Covers assignment requirement 5 (BST/AVL to organize/search records
 * by Student ID).
 */
public class StudentBST {

    private BSTNode root;
    private int size;

    public StudentBST() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a new student into the tree, ordered by Student ID.
     *
     * @return true if inserted, false if a student with the same ID
     *         already exists (duplicates rejected — requirement 14).
     */
    public boolean insert(Student student) {
        if (student == null || student.getStudentId() == null) {
            return false;
        }
        if (search(student.getStudentId()) != null) {
            return false; // Duplicate ID
        }
        root = insertRecursive(root, student);
        size++;
        return true;
    }

    private BSTNode insertRecursive(BSTNode node, Student student) {
        if (node == null) {
            return new BSTNode(student);
        }
        int comparison = student.getStudentId().compareTo(node.getStudent().getStudentId());
        if (comparison < 0) {
            node.setLeft(insertRecursive(node.getLeft(), student));
        } else if (comparison > 0) {
            node.setRight(insertRecursive(node.getRight(), student));
        }
        // comparison == 0 (duplicate) is already prevented above
        return node;
    }

    /**
     * Searches the tree for a student by ID.
     *
     * @return the matching Student, or null if not found.
     */
    public Student search(String studentId) {
        if (studentId == null) {
            return null;
        }
        BSTNode current = root;
        while (current != null) {
            int comparison = studentId.compareTo(current.getStudent().getStudentId());
            if (comparison == 0) {
                return current.getStudent();
            } else if (comparison < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null; // Not found
    }

    /**
     * Removes a student from the tree by Student ID.
     *
     * @return true if the student was found and removed, false if no
     *         matching student ID exists.
     */
    public boolean delete(String studentId) {
        if (studentId == null || search(studentId) == null) {
            return false;
        }
        root = deleteRecursive(root, studentId);
        size--;
        return true;
    }

    private BSTNode deleteRecursive(BSTNode node, String studentId) {
        if (node == null) {
            return null;
        }

        int comparison = studentId.compareTo(node.getStudent().getStudentId());
        if (comparison < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), studentId));
        } else if (comparison > 0) {
            node.setRight(deleteRecursive(node.getRight(), studentId));
        } else {
            // Node to delete found

            // Case 1: no children
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // Case 2: one child
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            // Case 3: two children — replace with in-order successor
            // (smallest value in the right subtree)
            BSTNode successor = findMin(node.getRight());
            node.setStudent(successor.getStudent());
            node.setRight(deleteRecursive(node.getRight(), successor.getStudent().getStudentId()));
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Displays all students in ascending order of Student ID, using an
     * in-order traversal.
     * Satisfies menu item 8: "Display Students using BST/AVL".
     */
    public void displayAll() {
        if (root == null) {
            System.out.println("No student records found in the tree.");
            return;
        }
        System.out.println("---- Student Records (BST, ordered by ID) ----");
        int[] count = {1}; // wrapped in array so it can be mutated inside the recursive helper
        inOrderTraversal(root, count);
        System.out.println("Total records: " + size);
    }

    private void inOrderTraversal(BSTNode node, int[] count) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.getLeft(), count);
        System.out.println(count[0] + ". " + node.getStudent());
        count[0]++;
        inOrderTraversal(node.getRight(), count);
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
