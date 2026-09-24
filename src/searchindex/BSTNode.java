package searchindex;

import common.Student;

/**
 * A single node in the Binary Search Tree, keyed by Student ID.
 */
public class BSTNode {

    private Student student;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(Student student) {
        this.student = student;
        this.left = null;
        this.right = null;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }
}
